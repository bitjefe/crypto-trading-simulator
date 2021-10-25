// <option value="1">One</option>

const fs = require('fs');

const response = JSON.parse(fs.readFileSync('./sampleApiResponse.json'));

function cryptoDropdown() {
  response.data.map(d => {
    return {
      name: d.name,
      ticker: d.symbol,
      price: d.quote.USD.price
    };
  }).forEach(d => {
    console.log(`<option data-price="${d.price}" value="${d.ticker}">${d.name} - ${d.ticker} - \$${d.price.toFixed(2)}</option>`)
  });
}

function table() {
  response.data.forEach(d => {
    console.log(
      `<tr>
      <td>${d.name}</td>
      <td>${d.quote.USD.price.toFixed(2)}</td>
      <td>${d.quote.USD.percent_change_1h}</td>
      <td>${d.quote.USD.percent_change_24h}</td>
      <td>${d.quote.USD.percent_change_7d}</td>
      <td>${d.quote.USD.market_cap_dominance}</td>
      </tr>`
    );
  });
}

table();