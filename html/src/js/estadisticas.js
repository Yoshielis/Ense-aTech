// JavaScript para estadísticas
document.addEventListener('DOMContentLoaded', function() {
    inicializarGraficas();
});

function inicializarGraficas() {
    const ctx = document.getElementById('rendimientoChart').getContext('2d');

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Ana G.', 'Carlos L.', 'María R.', 'Pedro M.', 'Laura H.'],
            datasets: [{
                label: 'Calificación',
                data: [4.8, 4.2, 3.8, 4.5, 4.0],
                backgroundColor: 'rgba(67, 97, 238, 0.8)',
                borderColor: 'rgba(67, 97, 238, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    max: 5
                }
            }
        }
    });
}

function actualizarEstadisticas() {
    const curso = document.getElementById('cursoSelect').value;
    const periodo = document.getElementById('periodoSelect').value;

    alert(`Actualizando estadísticas para: ${curso} - ${periodo}`);
    // Aquí iría la lógica para actualizar las gráficas
}

function exportarReporte() {
    alert('Generando y descargando reporte de estadísticas...');
}