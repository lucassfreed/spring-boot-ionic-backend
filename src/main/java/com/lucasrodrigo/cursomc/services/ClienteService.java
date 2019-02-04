package com.lucasrodrigo.cursomc.services;

import com.lucasrodrigo.cursomc.domain.Cidade;
import com.lucasrodrigo.cursomc.domain.Cliente;
import com.lucasrodrigo.cursomc.domain.Endereco;
import com.lucasrodrigo.cursomc.domain.enums.Perfil;
import com.lucasrodrigo.cursomc.domain.enums.TipoCliente;
import com.lucasrodrigo.cursomc.dto.ClienteDTO;
import com.lucasrodrigo.cursomc.dto.ClienteNewDTO;
import com.lucasrodrigo.cursomc.repositories.ClienteRepository;
import com.lucasrodrigo.cursomc.repositories.EnderecoRepository;
import com.lucasrodrigo.cursomc.security.UserSS;
import com.lucasrodrigo.cursomc.services.exceptions.AuthorizationException;
import com.lucasrodrigo.cursomc.services.exceptions.DataIntegrityException;
import com.lucasrodrigo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

    public Cliente find(Integer id) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasHole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateDate(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        find(id);

        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados!");
        }
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Cliente findByEmail(String email) {

        UserSS user = UserService.authenticated();
        if (user == null || !user.hasHole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }

        Cliente obj = repo.findByEmail(email);
        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
        }
        return obj;

    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDto(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }

    public Cliente fromDto(ClienteNewDTO objDto) {
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());
        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }

        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }

        return cli;
    }

    private void updateDate(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

}
