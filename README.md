awk -F'[][]' '$2 ~ /27\/Apr\/2025/ && /POST \/test\/login/ {
    split($2, t, /[: ]/);
    hour = t[4]; min = t[5];
    if ((hour == 20 && min >= 37) || (hour == 21 && min <= 37)) print
}' access_log




I hope you are doing well.



We are currently evaluating FactSet’s REST API for the market data.

We are able to connect to factset api. We are able to access the token generation , get price data and get ask bid price endpoints that you have shared with us. 


We nee your assistance in clarifying the capabilities of the REST API that has been shared with us.
Currentlly --- application fetching real-time/delayed market data (such as price, volume, open, high, low, trade time, etc.) for 90+ companies, with updates every 5 seconds. 

Ideally, we would prefer a streaming-based solution where we can subscribe to updates per company, rather than polling via REST API at high frequency.

Could you please confirm the following:

Does the REST API support streaming updates, or is it designed only for on-demand polling?
Can we subscribe to multiple symbols (e.g., 100 companies) and receive updates every 5 seconds, ideally using the _subscriptionMinimumInterval parameter? If yes , how this will work we need more detail on implementation point of view.
Are there any rate limits, throttling policies, or bandwidth recommendations we should be aware of for this use case?

We want to implement an efficient and scalable approach and would appreciate your guidance on the best API product to meet these requirements.

Looking forward to your response.
