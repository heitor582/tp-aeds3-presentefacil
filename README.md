# Relatório do Trabalho Prático - Presentefacil

## Participantes
- Caio Fernandes da Silva
- Gustavo Lopes Oliveira
- Heitor Canhestro Vieira Moreira
- Lucas Soares Tavares

## Descrição do Sistema
O sistema Presentefacil é uma aplicação para gerenciamento de listas de presentes, permitindo que usuários criem, editem e compartilhem listas vinculadas ao seu perfil. O objetivo é facilitar a organização de eventos e presentes, tornando o processo mais eficiente e colaborativo.


### Funcionalidades Principais
- Cadastro, edição, exclusão e consulta de usuários.
- Cadastro, edição, exclusão e consulta de listas de presentes.
- Vinculação de listas aos usuários por meio do campo `idUsuario`.
- Compartilhamento de listas via código NanoID.
- Visualização de listas de outros usuários utilizando o código NanoID.

### Telas do Sistema
![Menu Principal](imagens/menuPrincipal.png)
![Tela de Dados do Usuário](imagens/meusDados.png)
![Tela de Listas de Presentes](imagens/minhasListas.png)
![Dados das listas](imagens/listaExemplo.png)
![Tela de Compartilhamento por NanoID](imagens/buscaLista.png)

### Classes Criadas
- `Usuario`
- `ListaPresente`
- `ArquivoIndexado`
- `TabelaHashExtensivel`
- `ArvoreBMais`
- `NanoID`
- `SistemaPresentefacil`

### Operações Especiais Implementadas
- CRUD completo para usuários e listas, ambos estendendo `ArquivoIndexado`.
- Índices diretos e indiretos utilizando Tabelas Hash Extensíveis e Árvores B+.
- Relacionamento 1:N entre usuários e listas registrado por Árvore B+.
- Compartilhamento seguro de listas via NanoID.
- Visualização de listas de terceiros sem expor dados sensíveis.

## Checklist

- Há um CRUD de usuários (que estende a classe ArquivoIndexado, acrescentando Tabelas Hash Extensíveis e Árvores B+ como índices diretos e indiretos conforme necessidade) que funciona corretamente?  
    **Sim.** O CRUD de usuários foi implementado conforme especificado, utilizando índices para otimizar buscas.

- Há um CRUD de listas (que estende a classe ArquivoIndexado, acrescentando Tabelas Hash Extensíveis e Árvores B+ como índices diretos e indiretos conforme necessidade) que funciona corretamente?  
    **Sim.** O CRUD de listas está funcional e utiliza os índices para garantir eficiência.

- As listas de presentes estão vinculadas aos usuários usando o idUsuario como chave estrangeira?  
    **Sim.** Cada lista possui o campo `idUsuario` como chave estrangeira.

- Há uma árvore B+ que registre o relacionamento 1:N entre usuários e listas?  
    **Sim.** O relacionamento é registrado e consultado via Árvore B+.

- Há uma visualização das listas de outras pessoas por meio de um código NanoID?  
    **Sim.** O sistema permite visualizar listas de terceiros usando NanoID.

- O trabalho compila corretamente?  
    **Sim.** O projeto compila sem erros.

- O trabalho está completo e funcionando sem erros de execução?  
    **Sim.** Todas as funcionalidades foram testadas e estão operacionais.

- O trabalho é original e não a cópia de um trabalho de outro grupo?  
    **Sim.** O trabalho é original, desenvolvido integralmente pelo grupo.
