# Esercizio di risoluzione di un merge conflict

**Il tempo massimo in laboratorio per questo esercizio è di _20 minuti_.
Se superato, sospendere l'esercizio e riprenderlo per ultimo!**

Si visiti https://github.com/APICe-at-DISI/OOP-git-merge-conflict-test.
Questo repository contiene due branch: `master` e `feature`

Per ognuna delle seguenti istruzioni, si annoti l'output ottenuto.
Prima di eseguire ogni operazione sul worktree o sul repository,
si verifichi lo stato del repository con `git status`.

1. Si cloni localmente il repository

```shell
▶ git clone https://github.com/APICe-at-DISI/OOP-git-merge-conflict-test.git
Cloning into 'OOP-git-merge-conflict-test'...
remote: Enumerating objects: 12, done.
remote: Counting objects: 100% (4/4), done.
remote: Compressing objects: 100% (3/3), done.
remote: Total 12 (delta 1), reused 1 (delta 1), pack-reused 8 (from 1)
Receiving objects: 100% (12/12), done.
Resolving deltas: 100% (2/2), done.
```

2. Ci si assicuri di avere localmente entrambi i branch remoti

```shell
▶ git branch -a
* master
  remotes/origin/HEAD -> origin/master
  remotes/origin/feature
  remotes/origin/master
```

3. Si faccia il merge di `feature` dentro `master`, ossia: si posizioni la `HEAD` su `master`
   e da qui si esegua il merge di `feature`

```shell
▶ git merge origin/feature
Auto-merging HelloWorld.java
CONFLICT (content): Merge conflict in HelloWorld.java
Automatic merge failed; fix conflicts and then commit the result.
▶ git status
On branch master
Your branch is up to date with 'origin/master'.

You have unmerged paths.
  (fix conflicts and run "git commit")
  (use "git merge --abort" to abort the merge)

Unmerged paths:
  (use "git add <file>..." to mark resolution)
        both modified:   HelloWorld.java

no changes added to commit (use "git add" and/or "git commit -a")
```

4. Si noti che viene generato un **merge conflict**!
5. Si risolva il merge conflict come segue:
   - Il programma Java risultante deve stampare sia il numero di processori disponibili
     (funzionalità presente su `master`)
     che il nome dell'autore del file
     (funzionalità presente su `feature`)

```shell
▶ cat HelloWorld.java
public final class HelloWorld {

        private static final String AUTHOR = "Danilo Pianini";

        public static void main(final String[] args) {
                System.out.println("This program is running in a PC with " + procNumber() + " logic processors!");
                System.out.println("This program has been realised by " + AUTHOR);
        }

        public static int procNumber() {
                return Runtime.getRuntime().availableProcessors();
        }

}
▶ git status
On branch master
Your branch is up to date with 'origin/master'.

You have unmerged paths.
  (fix conflicts and run "git commit")
  (use "git merge --abort" to abort the merge)

Unmerged paths:
  (use "git add <file>..." to mark resolution)
        both modified:   HelloWorld.java

no changes added to commit (use "git add" and/or "git commit -a")
```

6. Si crei un nuovo repository nel proprio github personale
7. Si aggiunga il nuovo repository creato come **remote** e si elenchino i remote

```shell
▶ git remote add exercise https://github.com/FireSoul04/61-git-remotes-merge-conflict.git
▶ git remote -v
exercise        https://github.com/FireSoul04/61-git-remotes-merge-conflict.git (fetch)
exercise        https://github.com/FireSoul04/61-git-remotes-merge-conflict.git (push)
origin  https://github.com/APICe-at-DISI/OOP-git-merge-conflict-test.git (fetch)
origin  https://github.com/APICe-at-DISI/OOP-git-merge-conflict-test.git (push)
```

8. Si faccia push del branch `master` sul proprio repository

```shell
▶ git push -u exercise master
Enumerating objects: 9, done.
Counting objects: 100% (9/9), done.
Delta compression using up to 12 threads
Compressing objects: 100% (6/6), done.
Writing objects: 100% (9/9), 1012 bytes | 1012.00 KiB/s, done.
Total 9 (delta 1), reused 9 (delta 1), pack-reused 0
remote: Resolving deltas: 100% (1/1), done.
To https://github.com/FireSoul04/61-git-remotes-merge-conflict.git
 * [new branch]      master -> master
Branch 'master' set up to track remote branch 'master' from 'exercise'.
▶ git status
On branch master
Your branch is up to date with 'exercise/master'.

You have unmerged paths.
  (fix conflicts and run "git commit")
  (use "git merge --abort" to abort the merge)

Unmerged paths:
  (use "git add <file>..." to mark resolution)
        both modified:   HelloWorld.java

no changes added to commit (use "git add" and/or "git commit -a")
```

9. Si setti il branch remoto `master` del nuovo repository come *upstream* per il proprio branch `master` locale

```shell
▶ git branch --set-upstream-to=exercise/master master
Branch 'master' set up to track remote branch 'master' from 'exercise'.
▶ git add .
▶ git commit -m "Resolved conflict"
[master 1b1245c] Resolved conflicts
▶ git push
Enumerating objects: 10, done.
Counting objects: 100% (10/10), done.
Delta compression using up to 12 threads
Compressing objects: 100% (6/6), done.
Writing objects: 100% (6/6), 719 bytes | 719.00 KiB/s, done.
Total 6 (delta 2), reused 1 (delta 0), pack-reused 0
remote: Resolving deltas: 100% (2/2), completed with 1 local object.
To https://github.com/FireSoul04/61-git-remotes-merge-conflict.git
   8e0f29c..1b1245c  master -> master
```
