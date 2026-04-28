# 📱 Projet Android — Application de QCM

## 🎓 Contexte

Ce projet a été réalisé dans le cadre du module **Développement Mobile Android** (M2 I2L).
Il s’agit d’une application Android permettant à un utilisateur de répondre à des **questionnaires à choix multiples (QCM)**, organisés par **catégories**, avec gestion des scores et affichage des résultats.

---

## 🎯 Objectifs du projet

L’application vise à :

* Proposer plusieurs **questionnaires thématiques**
* Présenter les questions **une par une**
* Forcer la validation d’une réponse avant de continuer
* Calculer et mémoriser les **scores**
* Afficher les résultats et la **moyenne générale**
* Préparer une gestion **administrateur** pour l’ajout de questionnaires

---

## 🧩 Fonctionnalités implémentées

### ✅ Fonctionnalités actuellement disponibles

* 📋 Lecture de questionnaires depuis des fichiers `.txt`
* 🔀 Enchaînement automatique des questions
* ☑️ Obligation de sélectionner une réponse avant validation
* 📊 Calcul des scores par questionnaire
* 💾 Sauvegarde persistante des scores via `SharedPreferences`
* 📈 Affichage des scores par catégorie
* 🧮 Calcul automatique de la moyenne sur 20
* 🔄 Réinitialisation possible des scores
* 🏠 Navigation entre les différentes activités

---

## ⏳ Fonctionnalités en cours / à venir

* 📑 Sélection du questionnaire depuis une liste dédiée
* 🚫 Blocage des questionnaires déjà complétés
* ⛔ Bouton *Stopper* mettant le score à 0
* 🔐 Accès administrateur avec mot de passe (`MDP`)
* ➕ Ajout de nouveaux questionnaires via l’interface admin
* 💾 Sauvegarde avancée des questionnaires ajoutés
* 🎨 Améliorations ergonomiques (UI/UX)

---

## 🗂️ Structure du projet

```
app/
 └── src/
     └── main/
         ├── java/com/example/projetmouammar/
         │   ├── MainActivity.java
         │   ├── QuizActivity.java
         │   ├── ScoreActivity.java
         │   ├── ScoreManager.java
         │   └── Question.java
         │
         ├── assets/
         │   ├── qcm01.txt
         │   ├── qcm02.txt
         │   └── qcm03.txt
         │
         └── res/layout/
             ├── activity_main.xml
             ├── activity_quiz.xml
             └── activity_score.xml
```

---

## 📄 Format des fichiers QCM

Les questionnaires sont stockés dans le dossier `assets/` et respectent le format suivant :

```
Nom de la catégorie

Énoncé de la question

Réponse 1
Réponse 2 x
Réponse 3
```

👉 Le caractère `x` indique la **bonne réponse**.

---

## 🛠️ Technologies utilisées

* **Langage** : Java
* **IDE** : Android Studio
* **UI** : ConstraintLayout, MaterialToolbar
* **Stockage** : SharedPreferences
* **API Android** : API 21+

---

## ▶️ Lancer le projet

1. Cloner le dépôt :

```bash
git clone <url-du-repo>
```

2. Ouvrir le projet avec **Android Studio**
3. Synchroniser Gradle
4. Lancer sur un émulateur ou un appareil Android

---

## 👤 Auteur

* **Nom** : Mouammar
* **Formation** : Master 2 I2L
* **Année** : 2025–2026

