# base-util
base util

发布到 gitee 仓库流程：
```
cd ~/IdeaProjects/others/base-util
mvn clean deploy
cd target/mvn-repo
git init
git add .
git commit -m "deploy new version"
git remote add origin git@gitee.com:cuiswing/maven-repository.git
git checkout -b base-util
git push origin base-util --force
```

gitee 仓库引用 url：
https://gitee.com/cuiswing/maven-repository/raw/base-util