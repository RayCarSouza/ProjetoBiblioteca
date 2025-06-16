const apiBase = 'http://localhost:8080/api';

// Tabs
const tabs = {
  bib: { btn: '#tab-bib', panel: '#section-bib' },
  livro: { btn: '#tab-livro', panel: '#section-livro' }
};

Object.entries(tabs).forEach(([key, { btn, panel }]) => {
  document.querySelector(btn).addEventListener('click', () => {
    Object.values(tabs).forEach(({ btn, panel }) => {
      document.querySelector(btn).classList.remove('active');
      document.querySelector(panel).classList.add('hidden');
    });
    document.querySelector(btn).classList.add('active');
    document.querySelector(panel).classList.remove('hidden');
  });
});

// Toast
function showToast(msg) {
  const toast = document.createElement('div');
  toast.className = 'toast';
  toast.innerText = msg;
  document.getElementById('toast-container').appendChild(toast);
  setTimeout(() => toast.remove(), 3000);
}

// BIBLIOTECÁRIOS
async function carregarBibliotecarios() {
  const lista = document.getElementById('lista-bib');
  const select = document.getElementById('livro-bib');
  lista.innerHTML = '';
  select.innerHTML = '<option value="">Selecione...</option>';

  try {
    const res = await fetch(`${apiBase}/bibliotecarios`);
    const biblios = await res.json();

    biblios.forEach(b => {
      const li = document.createElement('li');
      li.innerHTML = `
        <strong>${b.nome}</strong> - ${b.email}
        <button onclick="editarBib(${b.id})">✏️</button>
        <button onclick="excluirBib(${b.id})">🗑️</button>
      `;
      lista.appendChild(li);

      const option = document.createElement('option');
      option.value = b.id;
      option.innerText = b.nome;
      select.appendChild(option);
    });
  } catch (err) {
    showToast('Erro ao carregar bibliotecários');
  }
}

document.getElementById('form-bib').addEventListener('submit', async e => {
  e.preventDefault();
  const id = document.getElementById('bib-id').value;
  const nome = document.getElementById('bib-nome').value;
  const email = document.getElementById('bib-email').value;
  const metodo = id ? 'PUT' : 'POST';
  const url = id ? `${apiBase}/bibliotecarios/${id}` : `${apiBase}/bibliotecarios`;

  try {
    await fetch(url, {
      method: metodo,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ nome, email })
    });
    showToast('Salvo com sucesso!');
    document.getElementById('form-bib').reset();
    carregarBibliotecarios();
  } catch {
    showToast('Erro ao salvar');
  }
});

async function editarBib(id) {
  const res = await fetch(`${apiBase}/bibliotecarios/${id}`);
  const bib = await res.json();
  document.getElementById('bib-id').value = bib.id;
  document.getElementById('bib-nome').value = bib.nome;
  document.getElementById('bib-email').value = bib.email;
}

async function excluirBib(id) {
  await fetch(`${apiBase}/bibliotecarios/${id}`, { method: 'DELETE' });
  showToast('Excluído com sucesso!');
  carregarBibliotecarios();
}

// LIVROS
async function carregarLivros() {
  const lista = document.getElementById('lista-livro');
  lista.innerHTML = '';

  try {
    const res = await fetch(`${apiBase}/livros`);
    const livros = await res.json();

    livros.forEach(l => {
      const li = document.createElement('li');
      li.innerHTML = `
        <strong>${l.titulo}</strong> - ${l.autor} (${l.status})
        <br><small>${l.bibliotecario?.nome || 'Sem bibliotecário'}</small>
        <button onclick="editarLivro(${l.id})">✏️</button>
        <button onclick="excluirLivro(${l.id})">🗑️</button>
      `;
      lista.appendChild(li);
    });
  } catch {
    showToast('Erro ao carregar livros');
  }
}

document.getElementById('form-livro').addEventListener('submit', async e => {
  e.preventDefault();
  const id = document.getElementById('livro-id').value;
  const titulo = document.getElementById('livro-titulo').value;
  const autor = document.getElementById('livro-autor').value;
  const genero = document.getElementById('livro-genero').value;
  const status = document.getElementById('livro-status').value;
  const bibliotecarioId = document.getElementById('livro-bib').value;
  const metodo = id ? 'PUT' : 'POST';
  const url = id ? `${apiBase}/livros/${id}` : `${apiBase}/livros`;

  try {
    await fetch(url, {
      method: metodo,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ titulo, autor, genero, status, bibliotecarioId })
    });
    showToast('Livro salvo!');
    document.getElementById('form-livro').reset();
    carregarLivros();
  } catch {
    showToast('Erro ao salvar livro');
  }
});

async function editarLivro(id) {
  const res = await fetch(`${apiBase}/livros/${id}`);
  const l = await res.json();
  document.getElementById('livro-id').value = l.id;
  document.getElementById('livro-titulo').value = l.titulo;
  document.getElementById('livro-autor').value = l.autor;
  document.getElementById('livro-genero').value = l.genero;
  document.getElementById('livro-status').value = l.status;
  document.getElementById('livro-bib').value = l.bibliotecario?.id || '';
}

async function excluirLivro(id) {
  await fetch(`${apiBase}/livros/${id}`, { method: 'DELETE' });
  showToast('Livro excluído!');
  carregarLivros();
}

// Inicialização
carregarBibliotecarios();
carregarLivros();
