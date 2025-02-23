package com.ufc.reuso.processorchestrator.model;

public enum OrderStatus {
    PENDING,            // Pedido criado, aguardando processamento
    VALIDATING_STOCK,   // Verificando disponibilidade no estoque
    STOCK_CONFIRMED,    // Estoque validado com sucesso
    PAYMENT_PROCESSING, // Processando pagamento
    PAYMENT_CONFIRMED,  // Pagamento confirmado
    INVOICE_GENERATION, // Gerando nota fiscal
    COMPLETED,          // Pedido finalizado com sucesso
    FAILED,             // Pedido falhou em algum processo
    CANCELLED           // Pedido foi cancelado pelo usuário
}
