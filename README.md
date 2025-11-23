# EcoWork Monitor

Aplicativo Android para monitoramento de sensores em tempo real com sistema de alertas inteligentes a fim de garantir qualidade no Home Office.

## 📱 Sobre o Projeto

EcoWork Monitor é um aplicativo mobile desenvolvido em Kotlin que permite o acompanhamento de dados de sensores (temperatura, umidade, qualidade do ar e nível de ruído) com notificações automáticas quando os valores ultrapassam limites configuráveis.
O objetivo é assegurar uma maior qualidade no trabalho remoto, momento em que ficamos bastante tempo isolados fisicamente.

## ✨ Funcionalidades

- **Dashboard em Tempo Real**: Visualização dos dados atuais de todos os sensores
- **Sistema de Alertas**: Notificações automáticas quando valores ultrapassam os limites configurados
- **Histórico de Alertas**: Registro completo de todos os alertas gerados
- **Configurações Personalizáveis**: Defina seus próprios limites para cada tipo de sensor
- **Autenticação Segura**: Login com credenciais ou provedores externos
- **Tema Escuro**: Interface moderna com suporte a modo escuro

## 🛠️ Tecnologias

- **Linguagem**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Arquitetura**: Clean Architecture (Domain, Data, UI)
- **Injeção de Dependência**: Hilt
- **Navegação**: Navigation Compose
- **Persistência Local**: Room Database + DataStore
- **Async**: Kotlin Coroutines + Flow

## 📋 Requisitos

- Android Studio Hedgehog ou superior
- JDK 11
- Android SDK 34
- Gradle 8.5+

## 🚀 Como Executar

1. Clone o repositório:
```bash
git clone https://github.com/fiap-3esoa-grupo-2025/EcoWorkMonitorMobile.git
cd EcoWorkMonitorMobile
```

2. Abra o projeto no Android Studio

3. Sincronize as dependências do Gradle

4. Execute o app em um emulador ou dispositivo físico (API 24+)

## 📁 Estrutura do Projeto

```
app/src/main/java/com/ecoworkmonitor/mobile/
├── core/           # Componentes compartilhados e utilitários
├── data/           # Repositórios, DAOs e fontes de dados
├── domain/         # Modelos de domínio e casos de uso
└── ui/             # Telas e componentes de interface
    ├── auth/       # Autenticação
    ├── dashboard/  # Dashboard principal
    ├── history/    # Histórico de alertas
    └── settings/   # Configurações
```

## 📄 Licença

Este projeto é de uso educacional e demonstrativo.
