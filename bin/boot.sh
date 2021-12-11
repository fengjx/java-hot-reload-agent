#!/usr/bin/env bash

BASE=$(cd "$(dirname "$0")";pwd)
JVM_OPTS=""
AGENT_HOME=""

# exit shell with err_code
# $1 : err_code
# $2 : err_msg
exit_on_err()
{
    [[ ! -z "${2}" ]] && echo "${2}" 1>&2
    exit ${1}
}


# SYNOPSIS
#   rreadlink <fileOrDirPath>
# DESCRIPTION
#   Resolves <fileOrDirPath> to its ultimate target, if it is a symlink, and
#   prints its canonical path. If it is not a symlink, its own canonical path
#   is printed.
#   A broken symlink causes an error that reports the non-existent target.
# LIMITATIONS
#   - Won't work with filenames with embedded newlines or filenames containing
#     the string ' -> '.
# COMPATIBILITY
#   This is a fully POSIX-compliant implementation of what GNU readlink's
#    -e option does.
# EXAMPLE
#   In a shell script, use the following to get that script's true directory of origin:
#     trueScriptDir=$(dirname -- "$(rreadlink "$0")")
#
# Execute the function in a *subshell* to localize variables and the effect of `cd`.
rreadlink() (

  target=$1 fname= targetDir= CDPATH=

  { \unalias command; \unset -f command; } >/dev/null 2>&1
  [ -n "$ZSH_VERSION" ] && options[POSIX_BUILTINS]=on # make zsh find *builtins* with `command` too.

  while :; do # Resolve potential symlinks until the ultimate target is found.
      [ -L "$target" ] || [ -e "$target" ] || { command printf '%s\n' "ERROR: '$target' does not exist." >&2; return 1; }
      command cd "$(command dirname -- "$target")" # Change to target dir; necessary for correct resolution of target path.
      fname=$(command basename -- "$target") # Extract filename.
      [ "$fname" = '/' ] && fname='' # !! curiously, `basename /` returns '/'
      if [ -L "$fname" ]; then
        # Extract [next] target path, which may be defined
        # *relative* to the symlink's own directory.
        # Note: We parse `ls -l` output to find the symlink target
        #       which is the only POSIX-compliant, albeit somewhat fragile, way.
        target=$(command ls -l "$fname")
        target=${target#* -> }
        continue # Resolve [next] symlink target.
      fi
      break # Ultimate target reached.
  done
  targetDir=$(command pwd -P) # Get canonical dir. path
  # Output the ultimate target's canonical path.
  # Note that we manually resolve paths ending in /. and /.. to make sure we have a normalized path.
  if [ "$fname" = '.' ]; then
    command printf '%s\n' "${targetDir%/}"
  elif  [ "$fname" = '..' ]; then
    # Caveat: something like /var/.. will resolve to /private (assuming /var@ -> /private/var), i.e. the '..' is applied
    # AFTER canonicalization.
    command printf '%s\n' "$(command dirname -- "${targetDir}")"
  else
    command printf '%s\n' "${targetDir%/}/$fname"
  fi
)

set_env()
{
    if [ -z "${JAVA_HOME}" ]; then
        exit_on_err 1 "Can not find JAVA_HOME, please set \$JAVA_HOME bash env first."
    fi

    echo "[INFO] JAVA_HOME: ${JAVA_HOME}"
    if [ -z "${JAVA_HOME}" ]; then
        # try to find JAVA_HOME from java command
        local JAVA_COMMAND_PATH=$( rreadlink $(type -p java) )
        JAVA_HOME=$(echo "$JAVA_COMMAND_PATH" | sed -n 's/\/bin\/java$//p')
    fi

    export PATH=$JAVA_HOME/bin:$PATH

    # iterater throught candidates to find a proper JAVA_HOME at least contains tools.jar which is required by arthas.
    if [ ! -d "${JAVA_HOME}" ]; then
        JAVA_HOME_CANDIDATES=($(ps aux | grep java | grep -v 'grep java' | awk '{print $11}' | sed -n 's/\/bin\/java$//p'))
        for JAVA_HOME_TEMP in ${JAVA_HOME_CANDIDATES[@]}; do
            if [ -f "${JAVA_HOME_TEMP}/lib/tools.jar" ]; then
                JAVA_HOME=`rreadlink "${JAVA_HOME_TEMP}"`
                break
            fi
        done
    fi

    local JAVA_VERSION

    local IFS=$'\n'
    # remove \r for Cygwin
    local lines=$("${JAVA_HOME}"/bin/java -version 2>&1 | tr '\r' '\n')
    for line in $lines; do
      if [[ (-z $JAVA_VERSION) && ($line = *"version \""*) ]]
      then
        local ver=$(echo $line | sed -e 's/.*version "\(.*\)"\(.*\)/\1/; 1q')
        # on macOS, sed doesn't support '?'
        if [[ $ver = "1."* ]]
        then
          JAVA_VERSION=$(echo $ver | sed -e 's/1\.\([0-9]*\)\(.*\)/\1/; 1q')
        else
          JAVA_VERSION=$(echo $ver | sed -e 's/\([0-9]*\)\(.*\)/\1/; 1q')
        fi
      fi
    done

    # when java version less than 9, we can use tools.jar to confirm java home.
    # when java version greater than 9, there is no tools.jar.
    if [[ "$JAVA_VERSION" -lt 9 ]];then
      # possible java homes
      javaHomes=("${JAVA_HOME%%/}" "${JAVA_HOME%%/}/.." "${JAVA_HOME%%/}/../..")
      for javaHome in ${javaHomes[@]}
      do
          toolsJar="$javaHome/lib/tools.jar"
          if [ -f $toolsJar ]; then
              JAVA_HOME=$( rreadlink $javaHome )
              BOOT_CLASSPATH=-Xbootclasspath/a:$( rreadlink $toolsJar )
              break
          fi
      done
      [ -z "${BOOT_CLASSPATH}" ] && exit_on_err 1 "tools.jar was not found, so arthas could not be launched!"
      JVM_OPTS="${JVM_OPTS} ${BOOT_CLASSPATH}"
    fi

    JVM_OPTS="-Dinput.encoding=UTF-8 ${JVM_OPTS} "

}



watcher()
{
  echo "[INFO] start hot-reload-watcher"
  set_env

  export AGENT_HOME=$BASE
  echo "[INFO] AGENT_HOME: "${AGENT_HOME}
  pid=$1
  echo "[INFO] pid: "${pid}
  watch_path=$2
  echo "[INFO] watch path: "${watch_path}

  java ${JVM_OPTS} -jar hot-reload-watcher.jar --pid=${pid} --path=${watch_path}
}


server()
{
  echo "功能未实现，敬请期待"
}


case_opt=$1
shift

case ${case_opt} in
watcher)
    watcher "$@"
    ;;
server)
    server "$@"
    ;;
esac