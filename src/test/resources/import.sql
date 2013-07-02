-- Fichier chargé au démarrage du serveur si JPA est configuré en mode create,create-drop ou update

-- Table de paramétrage
INSERT INTO public.parameter (id, version, key, value) VALUES (0, 0, 'smtp.host', 'ptx.smtp.corp.sopra');
INSERT INTO public.parameter (id, version, key, value) VALUES (1, 0, 'smtp.port', '25');
INSERT INTO public.parameter (id, version, key, value) VALUES (2, 0, 'smtp.from', 'jutils@sopragroup.com');

-- Table des rôles
INSERT INTO public.role (id, version, code, description) VALUES (1, 0, 'ROLE_USER', 'Rôle par défaut des utilisateurs enregistrés');
INSERT INTO public.role (id, version, code, description) VALUES (2, 0, 'ROLE_ADMIN', 'Rôle d''aministrateur de l''application. Accès à toutes les opérations disponnibles.');