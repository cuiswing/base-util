# base-util
base util

发布到 gitee 仓库流程：
```
mvn clean deploy
cd target/mvn-repo
git init
git add .
git commit -m "deploy xxx 版本号"
git remote add origin git@gitee.com:cuiswing/maven-repository.git
git checkout -b base-util
git push origin base-util --force
```

gitee 仓库引用 url：
https://gitee.com/cuiswing/maven-repository/raw/base-util