using System;
using Dapr.Client;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

for (int i = 1; i <= 100; i++) {
    DateTimeOffset now = (DateTimeOffset)DateTime.UtcNow;

    var order = new Order(i, now.ToUnixTimeMilliseconds());
    using var client = new DaprClientBuilder().Build();

    // Publish an event/message using Dapr PubSub
    await client.PublishEventAsync("orderpubsub", "orders", order);
    Console.WriteLine("Published data: " + order);

    await Task.Delay(TimeSpan.FromMilliseconds(50));
}

public record Order([property: JsonPropertyName("orderId")] int OrderId, [property: JsonPropertyName("timeStamp")] long TimeStamp);
