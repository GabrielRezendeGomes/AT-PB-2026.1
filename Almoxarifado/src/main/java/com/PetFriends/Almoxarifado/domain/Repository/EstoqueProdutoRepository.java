package com.PetFriends.Almoxarifado.domain.Repository;

import com.PetFriends.Almoxarifado.domain.Model.EstoqueProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstoqueProdutoRepository extends JpaRepository<EstoqueProduto, Long> {

    Optional<EstoqueProduto> findByProdutoId(Long produtoId);

}
