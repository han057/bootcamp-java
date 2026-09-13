# Guía Práctica de Git

> Resumen operativo basado en los capítulos 1-3 de *Pro Git* (Chacon & Straub).
---

## 1. Instalación y primera configuración

**Instalar:**

```bash
# Linux (Debian/Ubuntu)
apt-get install git

# Linux (Fedora)
yum install git

# Mac / Windows: instaladores en https://git-scm.com/downloads
```

**Configurar identidad (obligatorio antes del primer commit):**

```bash
git config --global user.name "Tu Nombre"
git config --global user.email "tu@email.com"
```

**Editor por defecto (opcional):**

```bash
git config --global core.editor "code --wait"   # ejemplo con VS Code
```

**Ver configuración:**

```bash
git config --list
git config user.name
```

**Niveles de configuración** (de menor a mayor prioridad): `--system` (toda la máquina) → `--global` (tu usuario) → sin flag (solo el repo actual, `.git/config`).

**Ayuda:**

```bash
git help <comando>
git <comando> --help
```

---

## 2. Crear u obtener un repositorio

```bash
git init                                   # convierte el directorio actual en repo Git
git clone <url>                            # clona un repo existente
git clone <url> <nombre-carpeta>           # clona con otro nombre de carpeta
```

---

## 3. El flujo básico: modificar → preparar → confirmar

Cada archivo puede estar: **sin rastrear**, **sin modificar**, **modificado** o **preparado (staged)**.

```bash
git status              # estado completo
git status -s           # estado abreviado (?? nuevo, A preparado, M modificado)

git add <archivo>       # empieza a rastrear / prepara cambios de un archivo
git add .                # prepara todos los cambios del directorio actual
git add *.c

git diff                 # cambios NO preparados vs. último commit
git diff --staged        # cambios preparados vs. último commit (= --cached)

git commit -m "mensaje"       # confirma lo preparado
git commit -a -m "mensaje"    # prepara y confirma en un paso (solo archivos ya rastreados)
git commit --amend            # corrige/añade al último commit (mensaje y/o staging)
```

**Eliminar y renombrar:**

```bash
git rm <archivo>            # elimina del disco y del repo
git rm --cached <archivo>   # deja de rastrear pero conserva el archivo en disco
git mv origen destino       # renombra (equivale a mv + rm + add)
```

**Deshacer cosas:**

```bash
git reset HEAD <archivo>       # saca un archivo del área de preparación (deshace add)
git checkout -- <archivo>      # descarta cambios locales, vuelve a la última versión confirmada (DESTRUCTIVO)
```

> `git checkout -- archivo` sobrescribe el archivo sin posibilidad de recuperarlo: úsalo solo si estás seguro.

---

## 4. Ignorar archivos: `.gitignore`

```
# comentario
*.log          # ignora por extensión
!importante.log # excepción
/TODO          # solo el archivo TODO en la raíz
build/         # ignora un directorio completo
doc/**/*.txt   # ignora recursivamente
```

Plantillas listas por lenguaje: https://github.com/github/gitignore

---

## 5. Historial de commits

```bash
git log                          # historial completo
git log -p -2                    # con diffs, últimos 2 commits
git log --stat                   # resumen de archivos modificados
git log --oneline                # una línea por commit
git log --oneline --graph --decorate --all   # gráfico con ramas
git log --pretty=format:"%h - %an, %ar : %s"

git log --since=2.weeks
git log --author="nombre"
git log --grep="palabra clave"
git log -S"texto_en_el_codigo"
git log -- ruta/al/archivo        # limita a commits que tocaron esa ruta
```

---

## 6. Trabajar con repositorios remotos

```bash
git remote                 # lista nombres de remotos (ej. origin)
git remote -v              # con URLs
git remote add <nombre> <url>
git remote rename <viejo> <nuevo>
git remote rm <nombre>
git remote show <nombre>   # detalles: ramas rastreadas, estado

git fetch <remoto>         # trae datos nuevos, NO combina con tu trabajo
git pull                   # fetch + merge automático de la rama rastreada
git pull --rebase          # fetch + rebase en lugar de merge

git push <remoto> <rama>            # envía tu rama
git push origin serverfix:awesomebranch   # envía rama local con otro nombre remoto
git push origin --delete <rama>     # elimina una rama remota
```

---

## 7. Etiquetas (tags)

```bash
git tag                        # listar
git tag -l 'v1.8.5*'           # listar con patrón

git tag -a v1.4 -m "mensaje"   # etiqueta anotada (recomendada, guarda autor/fecha/mensaje)
git tag v1.4-lw                # etiqueta ligera (solo apunta a un commit)
git tag -a v1.2 <hash-commit>  # etiquetar un commit anterior

git show v1.4                  # ver info de una etiqueta

git push origin v1.5           # las etiquetas NO se envían automáticamente
git push origin --tags         # enviar todas las etiquetas

git checkout -b version2 v2.0.0   # "activar" una etiqueta (crea rama en ese punto)
```

---

## 8. Alias útiles

```bash
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status
git config --global alias.unstage 'reset HEAD --'
git config --global alias.last 'log -1 HEAD'
git config --global alias.visual '!gitk'   # ! para ejecutar comando externo
```

---

## 9. Ramas (branching)

### Conceptos operativos

- Una rama es solo un puntero móvil a un commit.
- `HEAD` es un puntero a la rama en la que estás situado.
- Crear/cambiar de rama es instantáneo y barato: úsalo con libertad.

### Crear, cambiar, listar, borrar

```bash
git branch                     # lista ramas (* = la activa)
git branch -v                  # con último commit de cada una
git branch --merged            # ramas ya fusionadas con la actual
git branch --no-merged         # ramas con trabajo pendiente de fusionar

git branch <nombre>            # crea una rama nueva (no cambia a ella)
git checkout <nombre>          # cambia a una rama
git checkout -b <nombre>       # crea + cambia en un solo paso

git branch -d <nombre>         # borra rama (solo si ya está fusionada)
git branch -D <nombre>         # borra forzando, aunque tenga trabajo sin fusionar
```

> Cambiar de rama reemplaza los archivos del directorio de trabajo. Si tienes cambios sin confirmar que chocan, Git bloqueará el cambio de rama. Confirma o guarda (stash) antes de saltar.

### Fusionar (merge)

```bash
git checkout master
git merge <rama>
```

- **Fast-forward**: si no hubo trabajo divergente, Git solo mueve el puntero.
- **Merge de 3 bandas**: si las ramas divergieron, Git crea un commit de fusión con dos padres.

**Conflictos:**

```bash
git status          # ver qué archivos están sin fusionar (unmerged)
```

El archivo en conflicto queda marcado así:

```
<<<<<<< HEAD
tu versión
=======
versión de la otra rama
>>>>>>> nombre-rama
```

Edita a mano, elige/combina el contenido, elimina los marcadores `<<<<<<<`, `=======`, `>>>>>>>`, y luego:

```bash
git add <archivo>     # marca el conflicto como resuelto
git commit            # concluye la fusión
```

Herramienta gráfica de resolución: `git mergetool`

### Flujos de trabajo típicos

1. **Ramas de largo recorrido**: `master` (estable) + `develop` (integración) + ramas de tema; se fusiona hacia arriba a medida que el código madura.
2. **Ramas puntuales (topic branches)**: rama corta por cada tarea/issue, se fusiona y se borra. Es el patrón recomendado en Git por lo barato que es crear/fusionar ramas.

### Ramas remotas y de seguimiento

```bash
git checkout -b <rama> origin/<rama>     # crea rama local que sigue una remota
git checkout --track origin/<rama>       # atajo equivalente
git branch -u origin/<rama>              # asigna/cambia el seguimiento de la rama actual

git branch -vv                           # muestra qué rama remota sigue cada rama local y si está ahead/behind
git fetch --all; git branch -vv          # refresca antes de comprobar ahead/behind
```

### Rebase (reorganizar)

Alternativa a `merge`: reaplica tus commits encima de otra rama, dejando un historial lineal.

```bash
git checkout <rama-tema>
git rebase master          # reaplica los commits de <rama-tema> sobre master
git checkout master
git merge <rama-tema>      # ahora será fast-forward
```

Rebase avanzado (mover una rama a otra base sin necesidad de estar en ella):

```bash
git rebase --onto master server client
git rebase master server
```

> **Regla de oro: nunca reorganices (`rebase`) commits que ya hayas enviado (`push`) a un repositorio compartido/público.** Si alguien más ya basó su trabajo en esos commits, reescribirlos les obliga a re-fusionar y genera un lío de historial duplicado.
>
> Si tu equipo usa rebase con frecuencia, activa `git config --global pull.rebase true` para que `git pull` haga rebase en vez de merge por defecto.

**¿Merge o rebase?** Usa rebase para limpiar tu propio trabajo local **antes** de compartirlo; usa merge (o simplemente no reorganices) una vez que el trabajo ya es público.

---

## 10. Referencia rápida (cheatsheet)

| Acción | Comando |
|---|---|
| Configurar identidad | `git config --global user.name/user.email` |
| Iniciar repo | `git init` |
| Clonar repo | `git clone <url>` |
| Ver estado | `git status -s` |
| Preparar cambios | `git add <archivo>` |
| Confirmar cambios | `git commit -m "msg"` |
| Ver diferencias | `git diff` / `git diff --staged` |
| Ver historial | `git log --oneline --graph --all` |
| Deshacer staging | `git reset HEAD <archivo>` |
| Descartar cambios locales | `git checkout -- <archivo>` |
| Añadir remoto | `git remote add origin <url>` |
| Traer sin combinar | `git fetch` |
| Traer y combinar | `git pull` |
| Enviar cambios | `git push origin <rama>` |
| Crear rama | `git checkout -b <rama>` |
| Cambiar de rama | `git checkout <rama>` |
| Fusionar rama | `git merge <rama>` |
| Reorganizar rama | `git rebase <rama-base>` |
| Borrar rama local | `git branch -d <rama>` |
| Borrar rama remota | `git push origin --delete <rama>` |
| Crear etiqueta | `git tag -a v1.0 -m "msg"` |
| Enviar etiquetas | `git push origin --tags` |
