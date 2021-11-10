function daysBetween(start, end) {
  let dates;
  for (dates = []; start <= end; start.setDate(start.getDate() + 1)) {
    dates.push(new Date(start));
  }
  return dates;
};

function initChart() {
  var ctx = document.getElementById('myChart').getContext('2d');
  const parsedMetrics = JSON.parse(window.metrics);
  const dropdownValue = document.querySelector('#timeframe-select').value;
  const dates = daysBetween(new Date(dropdownValue), new Date()).map(x => x.toISOString()).map(x => x.split('T')[0]);
  const counts = dates.map(d => (parsedMetrics[d] || []).length);
  
  new Chart(ctx, {
    type: 'line',
    data: {
      labels: dates,
      datasets: [{
        label: document.querySelector('#metric-select').value,
        data: counts
      }]
    }
  });
}

function updateQsParam(k, v) {
  var searchParams = new URLSearchParams(window.location.search);
  searchParams.set(k, v);
  window.location.search = searchParams.toString();
}

function findDropdownWithValue(v) {
  let foundText;
  document.querySelectorAll('#timeframe-select option').forEach(o => {
    if (o.value === v) {
      foundText = o.dataset.active;
    }
  });
  
  return foundText;
}

function timeframeChanged(e) {
  const text = findDropdownWithValue(e.target.value);
  updateQsParam('active', text);
}

document.querySelector('#timeframe-select').addEventListener('change', timeframeChanged);

function metricChanged(e) {
  updateQsParam('metric', e.target.value);
}

document.querySelector('#metric-select').addEventListener('change', metricChanged);
initChart();