# Air Quality Aplicação web 

A aplicação web foi desenvolvida utilizando o framework Next, expondo a funcionalidades de pesquisa de meterologia, com base uma lista de países, estados e cidades, de pesquisa da qualidade do Ar de acordo com uma determinada cidade e país. Além disso ainda fornece os dados de estatisticas sobre chamadas à cache fornecida e às APIs externas utilizadas.

## Instalação

Para executar a aplicação é necessário ter instalado o NodeJS e o Yarn. [Guia de Instalação](https://nodejs.org/en)

Após a instalação do NodeJS e do Yarn, basta seguir os seguintes passoa:

1. _Clonar o repositório_
2. _Navegar até o dirétorio da aplicação_

```bash
cd HW1/frontend #Caminho da raiz do repositório
```

3. _Instalar as dependências do projeto_:

```bash
yarn install
```

ou 

```bash
npm install
```

## Execução

Após a instalação das dependências, para executar a aplicação em modo de desenvolvimento, execute o seguinte comando:

```bash
yarn dev
```
ou 
    
```bash
npm run dev
```

Se quiser iniciar a aplicação em modo de produção, execute o seguinte comando:

```bash
yarn start
```

ou 

```bash
npm run start
```

A aplicação estará disponível em [http://localhost:3000](http://localhost:3000) com o seu browser.

## Frameworks e Bibliotecas 
A aplicação web foi desenvolvida usando as seguintes bibliotecas e frameworks:

- [Daisy UI](https://daisyui.com/): Uma biblioteca de componentes para Vue.js e React que oferece uma maneira fácil e rápida de criar interfaces de usuário atraentes e responsivas.
- [Tailwind CSS](https://tailwindcss.com/): Um framework CSS utilitário que fornece uma abordagem "sem-opinião" para a construção rápida de interfaces de usuário personalizadas e responsivas.
- [NextJS](https://nextjs.org/):  Uma biblioteca popular do React que facilita a construção de aplicativos React de página única (SPAs) com renderização do lado do servidor (SSR) e pré-renderização, proporcionando um melhor desempenho e SEO.

Cada uma destas frameworks e bibliotecas contribuiram para a construção da aplicação web, oferencendo uma combinação de facilidade de uso, rapidez e personalização para criar as interfaces para o utilizador.