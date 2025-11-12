const db = new LocalDB("Produto");

const inputGtin = document.getElementById("gtin");
const filtroGtin = document.getElementById("filtroGtin");

inputGtin.addEventListener("input", () => {
  inputGtin.value = inputGtin.value.replace(/\D/g, "");
});

filtroGtin.addEventListener("input", () => {
  filtroGtin.value = filtroGtin.value.replace(/\D/g, "");
});

function atualizarTabela() {
  let produtos = db.readAll();

  const nomeFiltro = document.getElementById("filtroNome").value.trim().toLowerCase();
  const gtinFiltro = document.getElementById("filtroGtin").value.trim();
  const statusFiltro = document.getElementById("filtroStatus").value;

  produtos = produtos.filter(p => {
    const nomeOk = !nomeFiltro || p.name.toLowerCase().includes(nomeFiltro);
    const gtinOk = !gtinFiltro || p.gtin.includes(gtinFiltro);
    const statusOk = statusFiltro === "" || p.status.toString() === statusFiltro;
    return nomeOk && gtinOk && statusOk;
  });

  const tbody = document.querySelector("#tabela-produtos tbody");
  tbody.innerHTML = "";

  for (const p of produtos) {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td class="py-2">${p.id}</td>
      <td class="py-2">${p.name}</td>
      <td class="py-2">${p.desc}</td>
      <td class="py-2">${p.gtin}</td>
      <td class="py-2 text-center">${p.status ? "Ativo" : "Inativo"}</td>
      <td class="py-2 flex justify-center gap-5">
        <button class="hover:text-lime-300" onclick="editarProduto(${p.id})"><i class="fas fa-pencil-alt"></i></button>
        <button class="hover:text-lime-300" onclick="alterarStatusProduto(${p.id})"><i class="fas ${p.status ? "fa-toggle-on" : "fa-toggle-off"}"></i></button>
      </td>
    `;
    tbody.appendChild(tr);
  }

  document.getElementById("arquivo").textContent = db.dump();
  mostrarArquivoBinario();
}

function adicionarProduto() {
  const name = document.getElementById("name").value.trim();
  const desc = document.getElementById("desc").value.trim();
  const gtin = document.getElementById("gtin").value.trim();

  if (!name && (!gtin || gtin.length !== 13)) {
    alert("Preencha o name e o GTIN-13 corretamente!");
    return;
  } else if (!name) {
    alert("Preencha o name corretamente!");
    return;
  } else if (!gtin || gtin.length !== 13) {
    alert("Preencha o GTIN-13 corretamente!");
    return;
  }

  const idEdicao = document.getElementById("idEdicao").value;
  if (idEdicao) {
    db.update({ id: parseInt(idEdicao), name, desc, gtin, status: 1 });
  } else {
    db.create({ name, desc, gtin, status: 1 });
  }

  limparFormulario();
  atualizarTabela();
}

function editarProduto(id) {
  const produto = db.read(id);
  if (produto) {
    document.getElementById("name").value = produto.name;
    document.getElementById("desc").value = produto.desc;
    document.getElementById("gtin").value = produto.gtin;
    document.getElementById("idEdicao").value = produto.id;
  }
}

function alterarStatusProduto(id) {
  const produto = db.read(id);
  if (produto) {
    produto.status = produto.status ? 0 : 1;
    db.update(produto);
    atualizarTabela();
  }
}

function excluirProduto(id) {
  if (confirm("Deseja realmente excluir este produto?")) {
    db.delete(id);
    atualizarTabela();
  }
}

function limparFormulario() {
  document.getElementById("name").value = "";
  document.getElementById("desc").value = "";
  document.getElementById("gtin").value = "";
  document.getElementById("idEdicao").value = "";
}

function limparFiltros() {
  document.getElementById("filtroNome").value = "";
  document.getElementById("filtroGtin").value = "";
  document.getElementById("filtroStatus").value = "";
  atualizarTabela();
}

function mostrarArquivoBinario() {
  const tabela = document.getElementById("tabela-bin");
  const tbody = tabela.querySelector("tbody");
  tbody.innerHTML = "";

  const produtos = db.readAll();
  let allBytes = [];

  const total = produtos.length;
  const header = [
    (total >> 24) & 0xFF,
    (total >> 16) & 0xFF,
    (total >> 8) & 0xFF,
    total & 0xFF
  ];

  for (let i = 7; i >= 0; i--)
    header.push(0xFF);

  let byteMeta = [];

  for (let i = 0; i < 4; i++)
    byteMeta.push(`QUANTIDADE DE PRODUTOS: ${total}`);

  for (let i = 0; i < 8; i++)
    byteMeta.push(`ENDEREÇO DOS ESPAÇOS LIVRES: FF${i.toString(16).padStart(2, "0").toUpperCase()}`);

  allBytes.push(...header);

  for (const p of produtos) {
    const bytes = db.toBytes(p);

    const name = p.name;
    const desc = p.desc;
    const gtin = p.gtin;

    // record length
    for (let i = 0; i < 2; i++)
      byteMeta.push(`TAMANHO DO REGISTRO: ${bytes.length - 2}`);

    // id (4 bytes)
    for (let i = 0; i < 4; i++)
      byteMeta.push(`ID: ${p.id}`);

    // name length (2 bytes)
    for (let i = 0; i < 2; i++)
      byteMeta.push(`TAMANHO DO NOME: ${name.length}`);

    // name bytes
    for (let i = 0; i < name.length; i++)
      byteMeta.push(`NOME: "${name}"`);

    // desc length (2 bytes)
    for (let i = 0; i < 2; i++)
      byteMeta.push(`TAMANHO DA DESCRIÇÃO: ${desc.length}`);

    // desc bytes
    for (let i = 0; i < desc.length; i++)
      byteMeta.push(`DESCRIÇÃO: "${desc}"`);

    // gtin (13 bytes)
    for (let i = 0; i < 13; i++)
      byteMeta.push(`GTIN: "${gtin}"`);

    // status
    byteMeta.push(`STATUS: ${p.status === 1 ? "Ativo" : "Inativo"}`);

    allBytes.push(...bytes);
  }

  // Monta a tabela de bytes
  for (let i = 0; i < allBytes.length; i += 16) {
    const tr = document.createElement("tr");
    for (let j = 0; j < 16; j++) {
      const td = document.createElement("td");
      td.className = "border border-zinc-500 w-1/16";
      const byte = allBytes[i + j];
      if (byte !== undefined) {
        const idx = i + j;
        td.textContent = byte.toString(16).padStart(2, "0").toUpperCase();
        td.dataset.index = idx;
        td.title = byteMeta[idx] || "";
        tr.appendChild(td);
      }
    }
    tbody.appendChild(tr);
  }

  const tabelaTexto = document.getElementById("tabela-texto").querySelector("tbody");
  tabelaTexto.innerHTML = "";

  // Monta a tabela de caracteres
  for (let i = 0; i < allBytes.length; i += 16) {
    const tr = document.createElement("tr");
    for (let j = 0; j < 16; j++) {
      const td = document.createElement("td");
      td.className = "border border-zinc-500 w-1/16";
      const byte = allBytes[i + j];

      if (byte !== undefined) {
        td.textContent = (byte >= 48 && byte <= 122) ? String.fromCharCode(byte) : ".";
        td.dataset.index = i + j;
        tr.appendChild(td);
      }
    }
    tabelaTexto.appendChild(tr);
  }

  adicionarHoverInterativo();
}

function adicionarHoverInterativo() {
  const bytesTd = document.querySelectorAll("#tabela-bin td");
  const charsTd = document.querySelectorAll("#tabela-texto td");

  function getRecordRange(idx) {
    let ptr = 12;

    if (idx >= ptr) {
      while (ptr < bytesTd.length) {
        const lenHigh = parseInt(bytesTd[ptr + 1].textContent, 16);
        const lenLow = parseInt(bytesTd[ptr + 2].textContent, 16);
        const recordSize = (lenHigh << 8) | lenLow;
        const start = ptr;
        const end = ptr + 3 + recordSize - 1;

        if (idx >= start && idx <= end) {
          return { start, end };
        }

        ptr = end + 1;
      }

      return null;
    } else {
      const start = 0;
      const end = ptr - 1;

      return { start, end };
    }
  };

  function highlightRecord(start, end, add) {
    for (let i = start; i <= end; i++) {
      if (bytesTd[i]) bytesTd[i].classList[add ? 'add' : 'remove']("bg-lime-300/20");
      if (charsTd[i]) charsTd[i].classList[add ? 'add' : 'remove']("bg-lime-300/20");
    }
  }

  bytesTd.forEach(td => {
    td.addEventListener("mouseenter", () => {
      const idx = td.dataset.index;

      const range = getRecordRange(idx);
      if (range) highlightRecord(range.start, range.end, true);

      td.classList.remove("bg-lime-300/20");
      td.classList.add("bg-lime-300", "text-zinc-950");
      if (charsTd[idx]) {
        charsTd[idx].classList.remove("bg-lime-300/20");
        charsTd[idx].classList.add("bg-lime-300", "text-zinc-950");
      }
    });
    td.addEventListener("mouseleave", () => {
      const idx = td.dataset.index;
      td.classList.remove("bg-lime-300", "text-zinc-950");
      if (charsTd[idx]) charsTd[idx].classList.remove("bg-lime-300", "text-zinc-950");

      const range = getRecordRange(idx);
      if (range) highlightRecord(range.start, range.end, false);
    });
  });

  charsTd.forEach(td => {
    td.addEventListener("mouseenter", () => {
      const idx = Number(td.dataset.index);

      const range = getRecordRange(idx);
      if (range) highlightRecord(range.start, range.end, true);

      td.classList.remove("bg-lime-300/20");
      td.classList.add("bg-lime-300", "text-zinc-950");
      if (bytesTd[idx]) {
        bytesTd[idx].classList.remove("bg-lime-300/20");
        bytesTd[idx].classList.add("bg-lime-300", "text-zinc-950");
      }
    });

    td.addEventListener("mouseleave", () => {
      const idx = Number(td.dataset.index);

      td.classList.remove("bg-lime-300", "text-zinc-950");
      if (bytesTd[idx]) bytesTd[idx].classList.remove("bg-lime-300", "text-zinc-950");

      const range = getRecordRange(idx);
      if (range) highlightRecord(range.start, range.end, false);
    });
  });
}

function gerarHeaderTabela(tabela) {
  const thead = tabela.querySelector("thead tr");
  thead.innerHTML = "";
  for (let i = 0; i < 16; i++) {
    const th = document.createElement("th");
    th.textContent = i.toString(16).toUpperCase().padStart(2, "0");
    thead.appendChild(th);
  }
}

function limparBancoDeDados() {
  if (confirm("Deseja realmente apagar todos os produtos?")) {
    db.clear();
    atualizarTabela();
  }
}

window.onload = () => {
  atualizarTabela();
  gerarHeaderTabela(document.getElementById("tabela-bin"));
  gerarHeaderTabela(document.getElementById("tabela-texto"));
  mostrarArquivoBinario();
};

