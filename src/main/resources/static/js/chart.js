function initChart() {
  var ctx = document.getElementById('myChart').getContext('2d');
  new Chart(ctx, {
    type: 'line',
    data: {
      labels: ['2021-10-23', '2021-10-24', '2021-10-25', '2021-10-26', '2021-10-27', '2021-10-28', '2021-10-29'],
      datasets: [{
        label: 'Sign Up',
        data: [13, 4, 17, 18, 3, 20, 29]
      }]
    }
  });
}