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

  const header = [0x00, 0x01, 0x02, 0x03];

  //allBytes.push(...header);

  for (const p of produtos) {
    const bytes = db.toBytes(p);
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
        td.textContent = byte.toString(16).padStart(2, "0").toUpperCase();
        td.dataset.index = i + j;
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

  bytesTd.forEach(td => {
    td.addEventListener("mouseenter", () => {
      const idx = td.dataset.index;
      td.classList.add("bg-lime-300", "text-zinc-950");
      if (charsTd[idx]) charsTd[idx].classList.add("bg-lime-300", "text-zinc-950");
    });
    td.addEventListener("mouseleave", () => {
      const idx = td.dataset.index;
      td.classList.remove("bg-lime-300", "text-zinc-950");
      if (charsTd[idx]) charsTd[idx].classList.remove("bg-lime-300", "text-zinc-950");
    });
  });

  charsTd.forEach(td => {
    td.addEventListener("mouseenter", () => {
      const idx = td.dataset.index;
      td.classList.add("bg-lime-300", "text-zinc-950");
      if (bytesTd[idx]) bytesTd[idx].classList.add("bg-lime-300", "text-zinc-950");
    });
    td.addEventListener("mouseleave", () => {
      const idx = td.dataset.index;
      td.classList.remove("bg-lime-300", "text-zinc-950");
      if (bytesTd[idx]) bytesTd[idx].classList.remove("bg-lime-300", "text-zinc-950");
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

