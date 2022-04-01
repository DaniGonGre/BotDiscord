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

        /**
         * Primero 3 creamos constantes, uno llamado token de tipo String y otros dos creados a partir de dos clases
         * sacadas de las librerias importadas con anterioridad, llamadas client y gateway. El token está recogido en
         * los args del programa, para conseguir esto presionamos el "Edit configurations..." en el desplegable de la
         * clase Bot y añadimos el link del token al apartado arguments.
         */

        final String token = args[0];
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        /**
         * Creamos el embed con un builder que nos devolverá el título y la imágen. Debemos copiar la imágen en el
         * fichero de nuestro proyecto para poder insertarla añadiendo su nombre después de "attachment://". Lo
         * utilizaremos en una de las condiciones.
         */

        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .title("Loki")
                .image("attachment://loki.jpg")
                .build();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();

            /**
             * El bot devolverá la palabra "uino!" si nosotros añadimos la palabra "!ping".
             */

            if ("!ping".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage("uino!").block();
            }

            /**
             * Si escribimos "!embed" el bot nos devolverá una imágen  sacada de "betterttv.com/emotes". Además si
             * hacemos click en el título nos llevará al enlace de un video de "youtube.com". También añadimos una
             * descrición al embed y como pie de foto la hora a la que se ha enviado el mensaje.
             */

            if ("!embed".equals(message.getContent())) {
                String IMAGE_URL = "https://cdn.betterttv.net/emote/5603401460094fe01db2e3ea/3x";
                String ANY_URL = "https://youtu.be/br0palt4zg8";
                final MessageChannel channel = message.getChannel().block();
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

            /**
             * Al escribir "!img" el bot insertará una imágen  recogida con un fileAsInputStream por esto creamos
             * un try catch por si no recoge ningún archivo. Después creamos el mensaje añadiéndole un pequeño
             * texto, el fichero con la imágen y el embed creado anteriormente.
             */

            if ("!img".equals(message.getContent())) {
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
