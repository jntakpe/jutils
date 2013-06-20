-- Fichier chargé au démarrage du serveur si JPA est configuré en mode create,create-drop ou update

-- Table des rôles
INSERT INTO public.role (id, version, code, description) VALUES (1, 0, 'ROLE_USER', 'Rôle par défaut des utilisateurs enregistrés');
INSERT INTO public.role (id, version, code, description) VALUES (2, 0, 'ROLE_ADMIN', 'Rôle d''aministrateur de l''application. Accès à toutes les opérations disponnibles.');