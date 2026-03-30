package com.levelup.app.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.levelup.app.exception.NotFoundException;
import com.levelup.app.models.Product;
import com.levelup.app.models.User;
import com.levelup.app.models.Venta;
import com.levelup.app.models.VentaDetalle;
import com.levelup.app.models.dtos.VentaDto;
import com.levelup.app.mappers.VentaMapper;
import com.levelup.app.repositories.ProductRepository;
import com.levelup.app.repositories.UserRepository;
import com.levelup.app.repositories.VentaDetalleRepository;
import com.levelup.app.repositories.VentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final ProductRepository productRepository;
    private final VentaRepository ventaRepository;
    private final VentaDetalleRepository ventaDetalleRepository;
    private final UserRepository userRepository;
    private final VentaMapper ventaMapper;

    @Transactional
    @Override
    public Venta createVenta(List<VentaDto> ventasDto) {
        if (ventasDto == null || ventasDto.isEmpty()) {
            throw new IllegalArgumentException("La venta no puede venir vacía");
        }

        // 1) Tomar el usuario una sola vez (si es la misma venta)
        String email = ventasDto.get(0).getEmail();
        User userDb = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado!"));

        // 2) Crear y guardar cabecera UNA sola vez
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setUser(userDb);
        venta = ventaRepository.save(venta);

        // 3) Crear detalles + descontar stock
        for (VentaDto dto : ventasDto) {
            if (!email.equals(dto.getEmail())) {
                throw new IllegalArgumentException("Todos los ítems deben pertenecer al mismo usuario");
            }
            if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
                throw new IllegalArgumentException("Cantidad inválida para el producto " + dto.getCode());
            }

            Product productDb = productRepository.findById(dto.getCode())
                    .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + dto.getCode()));

            long stockActual = productDb.getStock() == null ? 0L : productDb.getStock();
            if (stockActual < dto.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente para " + dto.getCode());
            }

            productDb.setStock(stockActual - dto.getCantidad());
            productRepository.save(productDb);

            VentaDetalle detalle = ventaMapper.toDetalleEntity(dto);
            detalle.setVenta(venta);
            detalle.setProduct(productDb);
            detalle.setPrecio(productDb.getPrecio()); 

            ventaDetalleRepository.save(detalle);
        }

        return venta;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Venta> findAll() {
        return (List<Venta>) ventaRepository.findAll();
    }

}
