#!/bin/sh
cd .

timestamp() {
  date +"at %H:%M:%S on %d/%m/%Y"
}
temp=$(echo git branch --show-current)
echo $temp
#eval x=($temp == "backup")
#echo $x

if [$temp == "backup"]
then
  echo "error"
  exit -1
fi
echo "mowsi"
git show-ref --verify --quiet refs/heads/backup

if [$? == 0]
then
  printf "NOT EXISTS\n"
  git stash
  git checkout backup
  git stash pop

else
  printf "EXISTS\n"
  git stash save "backup_stash"
  git stash branch temp-backup-branch

  git commit -am "Regular auto-commit $(timestamp)"
  git checkout backup
  git merge temp-backup-branch
  git branch -D temp-backup-branch
  git push

  git checkout $temp
  git stash pop "backup_stash"
fi
#git commit -am "Regular auto-commit $(timestamp)"
#
#git checkout -b $temp
#git stash pop

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