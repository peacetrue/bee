#!/bin/bash

#set -x

echo "设置系统环境变量"

if [[ -z $SHELL_CONF_LOCATION ]]; then
  SHELL_CONF_LOCATION="$HOME/${SHELL}rc"
  echo "export SHELL_CONF_LOCATION=$SHELL_CONF_LOCATION" >>"$SHELL_CONF_LOCATION"
fi

if [[ ! -f $SHELL_CONF_LOCATION ]]; then
  echo "不存在配置文件 $SHELL_CONF_LOCATION，请手动设置"
  exit 1
fi

sed "1d" env-vars.sh >>"$SHELL_CONF_LOCATION"

# 根据用户使用的 shell 名称，写入对应的配置文件
#shell_names=(bash zsh)
#shell_files=(bashrc zshrc)
#for ((i = 0; i < ${#shell_names[@]}; i++)); do
#  name=${shell_names[i]}
#  file=${shell_files[i]}
#  if [[ $SHELL = "/bin/$name" ]]; then
#    file="$HOME/.$file"
#    #    sed -i "/BEE/d" "$file"
#    export SHELL_CONF_LOCATION=$file
#    echo "执行示例命令刷新环境变量：source $file"
#    exit 0
#  fi
#done

#echo "ERROR: 不支持 $SHELL shell"
#exit 1
