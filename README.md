# Relatório do Trabalho Prático - Presentefacil

## Participantes
- Caio Fernandes da Silva
- Gustavo Lopes Oliveira
- Heitor Canhestro Vieira Moreira
- Lucas Soares Tavares

## Descrição Completa do Sistema

O Presentefacil é um sistema para gerenciamento de listas de presentes, voltado para facilitar a organização de eventos como aniversários, casamentos e confraternizações. Usuários podem criar, editar, consultar e excluir suas listas de presentes, além de compartilhar listas com outras pessoas por meio de um código NanoID, garantindo privacidade e praticidade. O sistema permite também visualizar listas de terceiros sem expor dados sensíveis, promovendo colaboração entre participantes de um evento.

## Telas do Sistema

O sistema conta com uma interface intuitiva, composta pelas seguintes telas principais:

- **Menu Principal:**  
  ![Menu Principal](imagens/menuPrincipal.png)

- **Tela de Dados do Usuário:**  
  ![Tela de Dados do Usuário](imagens/meusDados.png)

- **Tela de Listas de Presentes:**  
  ![Tela de Listas de Presentes](imagens/minhasListas.png)

- **Dados das listas:**  
  ![Dados das listas](imagens/listaExemplo.png)

- **Tela de Compartilhamento por NanoID:**  
  ![Tela de Compartilhamento por NanoID](imagens/buscaLista.png)

---
### TP2 

- **Tela de Produtos:**  
  ![Tela de Produtos](imagens/tela_produtos.png)

- **Tela de Dados de Produtos:**  
  ![Tela de Dados de Produtos](imagens/tela_dadosproduto.png)

- **Tela de Produtos inseridos na lista:**  
  ![Tela de Produtos inseridos na Lista](imagens/tela_produtolista.png)

- **Tela de Dados dos Produtos inseridos na lista:**  
  ![Tela de Produtos](imagens/tela_detalhesprodutolista.png)

## Classes Criadas

O projeto foi estruturado em diversas classes para garantir modularidade e organização. As principais são:

- `Usuario` (modela o usuário do sistema)
- `ListaPresente` (modela a lista de presentes)
- `Produto` (modela os produtos)
- `ArquivoIndexado` (classe base para persistência e indexação)
- `TabelaHashExtensivel` (implementa índice direto e indireto)
- `ArvoreBMais` (implementa índice para relacionamento 1:N)
- `NanoID` (gera códigos únicos para compartilhamento)
- `SistemaPresentefacil` (classe principal do sistema)

## Operações Especiais Implementadas

- **CRUD completo para usuários e listas:**  
  Ambas as entidades estendem `ArquivoIndexado`, garantindo persistência eficiente e operações de consulta, edição, exclusão e cadastro.

- **Índices diretos e indiretos:**  
  Utilização de Tabelas Hash Extensíveis e Árvores B+ para otimizar buscas e garantir integridade dos dados.

- **Relacionamento 1:N entre usuários e listas:**  
  Registrado e consultado via Árvore B+, permitindo que cada usuário possua múltiplas listas vinculadas pelo campo `idUsuario`.

- **Relacionamento N:N entre listas e produtos:**  
  Implementado por entidade de associação e duas Árvores B+, permitindo vincular múltiplos produtos a múltiplas listas de forma eficiente.

- **Compartilhamento seguro de listas via NanoID:**  
  Permite que listas sejam compartilhadas sem expor dados sensíveis, utilizando códigos únicos gerados pela classe `NanoID`.

- **Visualização de listas de terceiros:**  
  Usuários podem acessar listas de outros participantes por meio do código NanoID, sem acesso a informações privadas.

- **Soft Delete:**
  A função de exclusão foi implementada utilizando o conceito de Soft Delete, onde os registros são marcados com um atributo booleano chamado status em vez de serem removidos fisicamente dos arquivos. Isso permite preservar o histórico dos dados e facilita eventuais recuperações ou auditorias, sem comprometer a integridade do sistema.

## Checklist TP1

- **Há um CRUD de usuários (que estende a classe ArquivoIndexado, acrescentando Tabelas Hash Extensíveis e Árvores B+ como índices diretos e indiretos conforme necessidade) que funciona corretamente?**  
  Sim. O CRUD de usuários foi implementado conforme especificado, utilizando índices para otimizar buscas e garantir integridade.

- **Há um CRUD de listas (que estende a classe ArquivoIndexado, acrescentando Tabelas Hash Extensíveis e Árvores B+ como índices diretos e indiretos conforme necessidade) que funciona corretamente?**  
  Sim. O CRUD de listas está funcional e utiliza os índices para garantir eficiência e integridade.

- **As listas de presentes estão vinculadas aos usuários usando o idUsuario como chave estrangeira?**  
  Sim. Cada lista possui o campo `idUsuario` como chave estrangeira, garantindo o vínculo correto.

- **Há uma árvore B+ que registre o relacionamento 1:N entre usuários e listas?**  
  Sim. O relacionamento é registrado e consultado via Árvore B+, permitindo múltiplas listas por usuário.

- **Há uma visualização das listas de outras pessoas por meio de um código NanoID?**  
  Sim. O sistema permite visualizar listas de terceiros usando NanoID, sem expor dados sensíveis.

- **O trabalho compila corretamente?**  
  Sim. O projeto compila sem erros.

- **O trabalho está completo e funcionando sem erros de execução?**  
  Sim. Todas as funcionalidades foram testadas e estão operacionais.

- **O trabalho é original e não a cópia de um trabalho de outro grupo?**  
  Sim. O trabalho é original, desenvolvido integralmente pelo grupo.

---
## Checklist TP2

- **Há um CRUD de produtos (que estende a classe ArquivoIndexado, acrescentando Tabelas Hash Extensíveis e Árvores B+ como índices diretos e indiretos conforme necessidade) que funciona corretamente?**  
  Sim. O CRUD de produtos foi implementado conforme especificado, utilizando índices para otimizar buscas e garantir integridade.

- **Há um CRUD da entidade de associação ListaProduto (que estende a classe ArquivoIndexado, acrescentando Tabelas Hash Extensíveis e Árvores B+ como índices diretos e indiretos conforme necessidade) que funciona corretamente?**  
  Sim. O CRUD da entidade de associação ListaProduto está funcional e utiliza os índices para garantir eficiência e integridade.

- **A visão de produtos está corretamente implementada e permite consultas as listas em que o produto aparece (apenas quantidade no caso de lista de outras pessoas)?**  
  Sim. A visão de produtos está implementada corretamente, permitindo consultas as listas em que aparece e a quantidade de listas de outros usuários em que aparece.

- **A visão de listas funciona corretamente e permite a gestão dos produtos na lista?**  
  Sim. A visão das listas está funcional e permite a gestão dos produtos na própria lista, garantindo eficiência e otimização.

- **A integridade do relacionamento entre listas e produtos está mantida em todas as operações?**  
  Sim. A integridade do relacionamento N:N entre listas e produtos está mantida em todas as operações do sistema.

- **O trabalho compila corretamente?**  
  Sim. O projeto compila sem erros.

- **O trabalho está completo e funcionando sem erros de execução?**  
  Sim. Todas as funcionalidades foram testadas e estão operacionais.

- **O trabalho é original e não a cópia de um trabalho de outro grupo?**  
  Sim. O trabalho é original, desenvolvido integralmente pelo grupo.
