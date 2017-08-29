package br.com.alura.escolalura.escolalura.repositorys;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.alura.escolalura.escolalura.codecs.AlunoCodec;
import br.com.alura.escolalura.escolalura.models.Aluno;

@Repository
public class AlunoRepository {

	private MongoClient cliente;
	private MongoDatabase bancoDeDados;

	private void criarConexao() {
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		AlunoCodec alunoCodec = new AlunoCodec(codec);

		CodecRegistry registro = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(alunoCodec));

		MongoClientOptions options = MongoClientOptions.builder().codecRegistry(registro).build();

		cliente = new MongoClient("localhost:27017", options);
		bancoDeDados = cliente.getDatabase("test");
	}

	public void salvar(Aluno aluno) {
		criarConexao();
		MongoCollection<Aluno> alunos = this.bancoDeDados.getCollection("alunos", Aluno.class);

		if (aluno.getId() == null) {
			alunos.insertOne(aluno);

		} else {
			alunos.updateOne(Filters.eq("_id", aluno.getId()), new Document("$set", aluno));
		}

	}

	public List<Aluno> obterTodosAlunos() {
		criarConexao();
		MongoCollection<Aluno> alunos = this.bancoDeDados.getCollection("alunos", Aluno.class);

		MongoCursor<Aluno> resultado = alunos.find().iterator();

		List<Aluno> alunosEncontrados = new ArrayList<>();
		while (resultado.hasNext()) {
			Aluno aluno = resultado.next();
			alunosEncontrados.add(aluno);
		}

		return alunosEncontrados;
	}

	public Aluno obterAlunoPorId(String id) {
		criarConexao();
		MongoCollection<Aluno> alunos = this.bancoDeDados.getCollection("alunos", Aluno.class);

		Aluno resultado = alunos.find(Filters.eq("_id", new ObjectId(id))).first();

		return resultado;
	}

}
