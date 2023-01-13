#!/usr/bin/env bash

#下载模块仓库代码函数定义
function download() {
    #判断参数数量是否正确
    echo '下载参数0=' "$0" # 执行的文件名（包含文件路径）
    echo '下载参数1=' "$1" # 是否下载模块
    echo '下载参数2=' "$2" # git仓库地址
    echo '下载参数3=' "$3" # 模块名
    echo '下载参数4=' "$4" # 分支名称
    echo '脚本的参数个数=' "$#" # 脚本的参数个数

    if [ $# -lt 4 ]
    then
        echo '参数错误，参数1：是否下载模块，参数2：git仓库地址，参数3：模块名，参数4：分支名称'
        exit 1
    fi

    moduleDownload=$1
    moduleGitName=$2
    moduleName=$3
    moduleBranch=$4

    #判断是否下载模块
    if [ "${moduleDownload}" -eq 1 ]
    then
        echo "根据配置处理模块：${moduleDownload}=${moduleName}"
        # 打开modules目录
        cd modules || exit
        rm -rf "${moduleName}"
    else
        echo "根据配置不处理模块：${moduleDownload}=${moduleName}"
        return
    fi
    echo "克隆模块：${moduleName}，模块分支：${moduleBranch}，地址：${moduleGitName}"
    #克隆代码
    git clone "$2"

    #文件是否存在
    if [ -e "${moduleName}" ]
    then
       #进入目录
      cd "${moduleName}"/ || return
      #当前路径
      curPath=$(pwd)
      # shellcheck disable=SC2006
      curBase=`basename "${curPath}"`
      #判断目录是否存在
      if [ "${curBase}" == "${moduleName}" ]
      then
        echo "进入目录:${moduleName}"
        # 创建并且切换到指定分支
        git checkout -b "${moduleBranch}" origin/"${moduleBranch}"
        echo "下载成功！"
        cd ..
      else
        echo "下载失败：未拉取到内容！"
        exit 1
      fi

      #退出moduleName目录
      if [ $# -ne 4 ]
      then
          cd ..
      fi
      echo
    else
      echo "${moduleName}文件夹不存在"
    fi

}

#变量定义
paramsList=(devPdfium gitPdfium modulePdfium branchPdfium)

#读取modules.properties文件
while read line;
do
    for p in ${paramsList[*]}
    do
        if [[ ${line} =~ ${p} ]]
        then
            echo "读取的配置：${line}"
            eval "${line}"
        fi
    done
done < gradle.properties
#执行下载
echo
#下载Pdfium
# shellcheck disable=SC2154
download "$devPdfium" "$gitPdfium" "$modulePdfium" "$branchPdfium"
cd ..
cd ../../../..