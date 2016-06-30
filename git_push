#!/bin/sh

if [ $# -ne 2 ]; then
	echo "Usage: git_push <commit message> <branch>"
	exit
fi

MSG=$1
BRANCH=$2
git add . && git commit -m "$MSG" && git push origin $BRANCH
