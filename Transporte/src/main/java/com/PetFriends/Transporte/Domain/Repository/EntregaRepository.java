package com.PetFriends.Transporte.Domain.Repository;

import com.PetFriends.Transporte.Domain.Model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {

    Optional<Entrega> findByPedidoId(Long pedidoId);
}
