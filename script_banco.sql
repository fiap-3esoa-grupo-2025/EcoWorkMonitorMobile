-- 1. CRIAÇÃO DAS TABELAS
CREATE TABLE T_EWM_USUARIO (
    id_usuario INT PRIMARY KEY,
    nm_nome VARCHAR(100),
    ds_email VARCHAR(100),
    pw_senha VARCHAR(255) -- Guardar hash
);

CREATE TABLE T_EWM_SENSOR (
    id_sensor INT PRIMARY KEY,
    nm_tipo VARCHAR(50), -- Ex: Temperatura, Ruído
    ds_localizacao VARCHAR(50), -- Ex: Quarto, Sala
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES T_EWM_USUARIO(id_usuario)
);

CREATE TABLE T_EWM_LEITURA (
    id_leitura INT PRIMARY KEY,
    vl_valor DECIMAL(10,2), -- O valor lido (ex: 28.5 graus)
    dt_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_sensor INT,
    FOREIGN KEY (id_sensor) REFERENCES T_EWM_SENSOR(id_sensor)
);

CREATE TABLE T_EWM_ALERTA (
    id_alerta INT PRIMARY KEY,
    ds_mensagem VARCHAR(200), -- Ex: "Temperatura crítica!"
    dt_alerta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    st_resolvido CHAR(1), -- 'S' ou 'N'
    id_sensor INT,
    FOREIGN KEY (id_sensor) REFERENCES T_EWM_SENSOR(id_sensor)
);
