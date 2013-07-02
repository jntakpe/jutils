-- Fichier charg� au d�marrage du serveur si JPA est configur� en mode create,create-drop ou update

-- Table de param�trage
INSERT INTO public.parameter (id, version, key, value) VALUES (0, 0, 'smtp.host', 'ptx.smtp.corp.sopra');
INSERT INTO public.parameter (id, version, key, value) VALUES (1, 0, 'smtp.port', '25');
INSERT INTO public.parameter (id, version, key, value) VALUES (2, 0, 'smtp.from', 'jutils@sopragroup.com');

-- Table des r�les
INSERT INTO public.role (id, version, code, description) VALUES (1, 0, 'ROLE_USER', 'R�le par d�faut des utilisateurs enregistr�s');
INSERT INTO public.role (id, version, code, description) VALUES (2, 0, 'ROLE_ADMIN', 'R�le d''aministrateur de l''application. Acc�s � toutes les op�rations disponnibles.');