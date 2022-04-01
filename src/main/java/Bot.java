import discord4j.core.*;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;

public class Bot {

    public static void main(String[] args) {
        final String token = args[0];
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .title("Loki")
                .image("attachment:///loki.jpg")
                .build();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("/embed".equals(message.getContent())) {

                String IMAGE_URL = "https://cdn.betterttv.net/emote/5603401460094fe01db2e3ea/3x";
                String ANY_URL = "https://youtu.be/br0palt4zg8";
                final MessageChannel channel = message.getChannel().block();
                //final String channel = "adios";
                EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
                builder.author("Cangre", ANY_URL, IMAGE_URL);
                builder.image(IMAGE_URL);
                builder.title("Cangrejo");
                builder.url(ANY_URL);
                builder.description("un siri facendo pa");
                builder.thumbnail(IMAGE_URL);
                builder.footer("Hora", IMAGE_URL);
                builder.timestamp(Instant.now());
                channel.createMessage(builder.build()).block();
            }

        if ("/img".equals(message.getContent())) {
            final MessageChannel channel = message.getChannel().block();

            InputStream fileAsInputStream = null;
            try {
                fileAsInputStream = new FileInputStream("loki.jpg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            channel.createMessage(MessageCreateSpec.builder()
                    .content("El dios de las mentiras")
                    .addFile("loki.jpg", fileAsInputStream)
                    .addEmbed(embed)
                    .build()).subscribe();
        }
    });

            gateway.onDisconnect().block();
    }
}
