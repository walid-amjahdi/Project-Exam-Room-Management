# Rapport Final : Gestion des Réservations de Salles de Rattrapage

## I. Introduction

### Contexte

Dans un établissement universitaire, les séances de rattrapage nécessitent souvent une organisation particulière. Les enseignants doivent trouver une salle disponible, choisir une date, préciser le créneau horaire, puis attendre une validation administrative. Lorsque ce processus est fait manuellement, il peut devenir difficile à suivre, surtout lorsqu'il y a plusieurs enseignants, plusieurs salles et plusieurs demandes en même temps.

### Problématique

La principale difficulté est d'éviter les conflits de réservation, c'est-à-dire empêcher que deux séances soient planifiées dans la même salle, à la même date et sur des horaires qui se chevauchent. Il faut aussi simplifier le processus pour les enseignants et permettre à l'administration de suivre, approuver ou refuser les demandes de manière claire.

### Objectif du projet

L'objectif de ce projet est de développer une application web permettant de gérer les réservations de salles pour les séances de rattrapage. L'application sépare deux espaces : un espace enseignant pour faire et suivre les demandes, et un espace administrateur pour gérer les salles et valider les réservations.

---

## II. Architecture et Technologies

### Architecture globale

Le projet suit une architecture simple en trois parties :

- **Frontend React** : interface utilisateur accessible depuis le navigateur.
- **Backend Spring Boot** : API REST qui traite les requêtes et applique la logique métier.
- **Base de données H2** : stockage des utilisateurs, salles, réservations et séances de rattrapage.

Le frontend communique avec le backend à l'aide de requêtes HTTP via la Fetch API. Les données sont échangées au format JSON.

### Choix technologiques

Le backend est développé avec **Java 17**, **Spring Boot**, **Spring Data JPA** et **Lombok**. Spring Boot permet de créer rapidement des API REST structurées, tandis que Spring Data JPA simplifie l'accès aux données. Lombok réduit le code répétitif dans les entités.

Le frontend utilise **React**, **React Router DOM**, la **Fetch API** avec async/await, ainsi que du HTML classique avec des classes **Bootstrap** natives. Les composants React restent simples, sous forme de composants fonctionnels avec `useState` et `useEffect`.

La base de données utilisée est **H2** en mode fichier (`jdbc:h2:file:./examroom`). Ce choix facilite le lancement du projet, les tests et la démonstration pendant la soutenance, sans nécessiter une installation de serveur de base de données externe.

L'authentification est volontairement simple : l'utilisateur saisit son email et son mot de passe, puis le backend vérifie ces informations en base de données. Côté frontend, les informations de session utiles sont conservées dans le `localStorage`.

---

## III. Modélisation des Données (JPA)

### Héritage des utilisateurs

Le projet utilise une classe abstraite `User`, qui représente les informations communes aux utilisateurs : nom, email, mot de passe et rôle. Cette classe est étendue par deux entités :

- **Teacher** : représentant un enseignant.
- **Admin** : représentant un administrateur.

La stratégie d'héritage utilisée est **JOINED**. Cela permet d'avoir une table principale pour les informations communes des utilisateurs, puis des tables séparées pour les informations spécifiques aux enseignants et aux administrateurs. Ce choix permet de garder une structure claire tout en évitant la duplication des champs communs.

### Entités principales

Les principales entités du projet sont :

- **Room** : représente une salle avec son nom, sa capacité, son bâtiment, sa localisation et son état de disponibilité.
- **Reservation** : représente une demande de réservation avec une date, une heure de début, une heure de fin, un statut et une raison.
- **MakeupSession** : représente la séance de rattrapage associée à une réservation. Elle contient le module, le groupe concerné et l'année universitaire.

### Relations entre entités

Une réservation relie plusieurs éléments :

- un enseignant qui effectue la demande ;
- une salle demandée ;
- éventuellement un administrateur associé ;
- une séance de rattrapage contenant les détails pédagogiques.

Ainsi, l'entité `Reservation` joue un rôle central dans l'application, car elle relie les utilisateurs, les salles et les informations de séance.

---

## IV. Fonctionnalités Principales

### Espace Enseignant

L'enseignant peut se connecter avec son email et son mot de passe. Après connexion, il accède à un tableau de bord simple lui permettant de :

- consulter la liste des salles disponibles ;
- rechercher une salle par son nom ou son bâtiment ;
- faire une demande de réservation ;
- choisir une date et un créneau horaire fixe (08h-10h, 10h-12h, 14h-16h, 16h-18h) ;
- renseigner le module, le groupe et l'année universitaire ;
- consulter ses réservations ;
- modifier ou supprimer ses demandes encore en attente ;
- suivre le statut de chaque demande : en attente, approuvée ou rejetée.

Les créneaux horaires proposés sont fixes afin de simplifier la planification et de réduire les erreurs de saisie.

### Espace Administrateur

L'administrateur peut également se connecter avec son email et son mot de passe. Son espace permet principalement de gérer les salles et les demandes de réservation.

Il peut :

- ajouter une nouvelle salle ;
- consulter la liste des salles ;
- modifier les informations d'une salle ;
- supprimer une salle ;
- consulter la file d'attente des réservations en attente ;
- approuver une demande ;
- rejeter une demande.

Cette séparation entre l'espace enseignant et l'espace administrateur rend l'application plus claire et adaptée aux rôles réels dans un contexte universitaire.

### Filtres et recherche

Le projet propose aussi des fonctionnalités de recherche et de filtrage :

- recherche de salles par nom ou bâtiment ;
- filtrage des réservations par date ;
- filtrage des réservations par nom de salle ;
- filtrage combiné par date et salle.

Ces filtres facilitent la consultation lorsque le nombre de salles ou de demandes augmente.

---

## V. Défi Technique Principal : La Gestion des Conflits

Le principal défi technique du projet est la détection des conflits de réservation. Il ne suffit pas de vérifier qu'une salle est réservée à une date donnée ; il faut aussi vérifier si les horaires se chevauchent.

Lorsqu'un enseignant fait une demande, le backend vérifie via une **requête JPQL** s'il existe déjà une réservation pour :

- la même salle ;
- la même date ;
- un créneau horaire qui chevauche celui demandé ;
- un statut encore actif (en attente ou confirmé, les demandes rejetées sont exclues).

La logique repose sur les champs `startTime` et `endTime`. Deux créneaux sont considérés comme en conflit si le début du nouveau créneau est avant la fin d'un créneau existant, et si la fin du nouveau créneau est après le début du créneau existant.

Par exemple, une réservation de **08h00 à 10h00** entre en conflit avec une autre réservation de **09h00 à 11h00**, car les deux périodes se croisent. En revanche, une réservation de **10h00 à 12h00** ne chevauche pas une réservation de **08h00 à 10h00**.

Si un conflit est détecté, la réservation est refusée (le backend retourne une erreur 400) et elle n'est pas enregistrée en base de données. Cela permet de garantir la cohérence des données même si plusieurs utilisateurs utilisent l'application simultanément.

---

## VI. Conclusion

Ce projet a permis de réaliser une application web full-stack complète, avec une séparation claire entre frontend, backend et base de données. Il m'a permis de mieux comprendre :

- la conception d'une API REST avec Spring Boot ;
- la communication entre un frontend React et un backend via des requêtes HTTP ;
- la modélisation des données avec JPA et l'héritage JOINED ;
- la gestion des relations entre entités (ManyToOne, OneToOne, OneToMany) ;
- l'implémentation d'une règle métier concrète avec la détection des conflits horaires.

La partie la plus importante du projet a été la gestion des conflits de réservation, car elle représente une vraie règle métier qui nécessite une réflexion sur la modélisation des données et la formulation de requêtes JPQL.

Même si le projet reste volontairement simple et adapté à un cadre universitaire, il couvre les fonctionnalités principales attendues : authentification, gestion des rôles, CRUD des salles, réservation avec séance de rattrapage, validation administrative, filtres et prévention des conflits.

### Perspectives d'amélioration

Plusieurs améliorations pourraient être envisagées dans une version future :

- ajouter l'envoi automatique d'emails lors de l'approbation ou du rejet d'une réservation ;
- remplacer H2 par une base de données plus adaptée à une utilisation réelle, comme MySQL ou PostgreSQL ;
- améliorer la gestion des comptes utilisateurs avec une procédure d'inscription ;
- renforcer le mécanisme d'authentification et la gestion des sessions ;
- améliorer l'ergonomie de l'interface avec davantage de retours visuels et de notifications.

En conclusion, ce projet constitue une base fonctionnelle et cohérente pour gérer les réservations de salles de rattrapage dans un contexte universitaire.
