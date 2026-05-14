# Gestion des Réservations de Salles de Rattrapage

## Description
Ce projet permet de gérer les réservations de salles de rattrapage à l'université.
Il sépare les accès entre les enseignants et les administrateurs avec une connexion sécurisée par email et mot de passe.

**Fonctionnalités principales :**
- Authentification (Email / Mot de passe).
- Interface Enseignant : Demande de réservation (créneaux fixes), modification, suppression, et suivi du statut.
- Interface Admin : Gestion complète des salles (CRUD), validation ou rejet des demandes.
- Système anti-conflit : Blocage automatique des réservations sur le même créneau et la même salle.
- Recherche et filtrage : Filtrage des réservations par date et nom de salle.

## Prérequis
- Java 17
- Node.js + npm

## Lancer le backend
cd backend/app
mvn spring-boot:run

## Lancer le frontend
cd frontend
npm start

## Comptes de test (mot de passe: password123)
| Rôle   | Email                |
|--------|----------------------|
| Teacher | ahaytar@teacher.com |
| Teacher | amjahdi@teacher.com |
| Admin   | ahaytar@admin.com   |
| Admin   | amjahdi@admin.com   |
