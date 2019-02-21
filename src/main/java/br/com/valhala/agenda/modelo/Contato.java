package br.com.valhala.agenda.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public class Contato implements Serializable, Comparable<Contato> {

	public static class Builder {
		private Long id;
		private String nome;

		private Collection<Telefone> telefones = new HashSet<>();

		public Builder() {
			super();
		}

		public Contato build() {
			return new Contato(this);
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder nome(String nome) {
			this.nome = nome;
			return this;
		}

		public Builder comTelefone(Telefone telefone) {
			telefones.add(telefone);
			return this;
		}

		public Builder telefones(Collection<Telefone> telefones) {
			this.telefones = telefones;
			return this;
		}

	}

	private static final long serialVersionUID = -4637163948422075131L;

	private Long id;

	private String nome;

	private Collection<Telefone> telefones = new HashSet<>();

	private Contato(Builder builder) {
		super();
		id = builder.id;
		nome = builder.nome;
		telefones = builder.telefones;
	}

	public void adicionaTelefone(Telefone telefone) {
		telefones.add(telefone);
	}

	@Override
	public int compareTo(Contato o) {
		if (nome == null) {
			return -1;
		} else if ((o == null) || (o.nome == null)) {
			return 1;
		} else {
			return nome.compareTo(o.nome);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Contato)) {
			return false;
		}
		Contato other = (Contato) obj;
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		return true;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Collection<Telefone> getTelefones() {
		return telefones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	public void removeTelefone(Telefone telefone) {
		telefones.remove(telefone);
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Contato [");
		if (id != null) {
			builder2.append("id=").append(id).append(", ");
		}
		if (nome != null) {
			builder2.append("nome=").append(nome).append(", ");
		}
		if (telefones != null) {
			builder2.append("telefones=").append(telefones);
		}
		builder2.append("]");
		return builder2.toString();
	}

}
