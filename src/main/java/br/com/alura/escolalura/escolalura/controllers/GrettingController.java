package br.com.alura.escolalura.escolalura.controllers;

import br.com.alura.escolalura.escolalura.models.Aluno;
import br.com.alura.escolalura.escolalura.repositorys.AlunoRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "person") //Diz ao Swagger que esse é um endpoint e REST deve ser documentado
@RestController
@RequestMapping("/person/")
public class GrettingController {

    @Autowired
    private AlunoRepository repository;

    @ApiOperation(value = "Find person by ID" )
    //Diz ao Swagger que essa operação REST deve ser documentado
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{personId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Aluno get(@PathVariable(value = "personId") String personId){
        return repository.obterAlunoPorId(personId);
    }

    @ApiOperation(value = "Find all persons" )
    //Diz ao Swagger que essa operação REST deve ser documentado
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Aluno> findAll(){
        return repository.obterTodosAlunos();
    }

}