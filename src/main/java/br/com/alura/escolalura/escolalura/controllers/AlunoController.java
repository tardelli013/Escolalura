package br.com.alura.escolalura.escolalura.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.maps.errors.ApiException;

import br.com.alura.escolalura.escolalura.models.Aluno;
import br.com.alura.escolalura.escolalura.repositorys.AlunoRepository;
import br.com.alura.escolalura.service.GeolocalizacaoService;

@Controller
@ComponentScan({"br.com.alura.escolalura.service"})
public class AlunoController {

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private GeolocalizacaoService geolocalizacaoService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@GetMapping("aluno/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("aluno", new Aluno());

		return "aluno/cadastrar";
	}

	@PostMapping("aluno/salvar")
	public String salvar(@ModelAttribute Aluno aluno) {

		try {
			aluno.getContato().setCoordinates(geolocalizacaoService.obterLateLongPor(aluno.getContato()));
			alunoRepository.salvar(aluno);
		} catch (ApiException | InterruptedException | IOException e) {
			System.out.println("Endereco nao localizado");
			e.printStackTrace();
		}

		return "redirect:/";
	}

	@GetMapping("/aluno/listar")
	public String listar(Model model) {
		List<Aluno> alunos = alunoRepository.obterTodosAlunos();
		model.addAttribute("alunos", alunos);
		return "aluno/listar";
	}

	@GetMapping("/aluno/visualizar/{id}")
	public String visualizar(@PathVariable String id, Model model) {
		Aluno aluno = alunoRepository.obterAlunoPorId(id);
		model.addAttribute("aluno", aluno);

		return "aluno/visualizar";
	}

	@GetMapping("/aluno/pesquisarnome")
	public String pesquisarNome() {
		return "aluno/pesquisarnome";
	}

	@GetMapping("/aluno/pesquisar")
	public String pesquisar(@RequestParam("nome") String nome, Model model) {
		List<Aluno> alunos = alunoRepository.pesquisaPor(nome);
		model.addAttribute("alunos", alunos);
		return "aluno/pesquisarnome";
	}

}
