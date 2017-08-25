package br.com.alura.escolalura.escolalura.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.alura.escolalura.escolalura.models.Aluno;
import br.com.alura.escolalura.escolalura.repositorys.AlunoRepository;

@Controller
public class AlunoController {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	@GetMapping("aluno/cadastrar")
	public String cadastrar(Model model){
		model.addAttribute("aluno", new Aluno());
		
		return "aluno/cadastrar";
	}
	
	@PostMapping("aluno/salvar")
	public String salvar(@ModelAttribute Aluno aluno){
		
		alunoRepository.salvar(aluno);
		return "redirect:/";
	}
	
	@GetMapping("/aluno/listar")
	public String listar(Model model){
	  List<Aluno> alunos = alunoRepository.obterTodosAlunos();
	  model.addAttribute("alunos", alunos);
	  return "aluno/listar";
	}
	
}
