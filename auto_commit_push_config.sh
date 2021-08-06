#!/bin/sh
cd .
git branch --show-current
temp = $?
printf $temp
git show-ref --verify --quiet refs/heads/BACKUP

if $? == 1
then
  git checkout -b BACKUP
else
  git stash
  git checkout BACKUP
  git stash pop
  timestamp() {
    date +"at %H:%M:%S on %d/%m/%Y"
  }
fi
git commit -am "Regular auto-commit $(timestamp)"

git checkout -b $temp
git stash pop

#  git commit -m "commit_message"
# $? == 0 means local branch with <branch-name> exists.

#printf $?
#git merge backup
#git add --all
#timestamp() {
#  date +"at %H:%M:%S on %d/%m/%Y"
#}
#git commit -am "Regular auto-commit $(timestamp)"
#git push origin master