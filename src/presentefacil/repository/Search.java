package repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Search {
    private final InvertedList iv;
    public static final Set<String> STOP_WORDS = new HashSet<>(Set.of(
    "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "?", 
    "a", "à", "agora", "ainda", "além", "algmas", "alguns", "ali", "ambos", "antes", "apenas", "apoio", 
    "aquela", "aquelas", "aquele", "aqueles", "aqui", "as", "assim", "até", "atrás", 
    "bem", "bom", 
    "cada", "cá", "coisa", "com", "como", "contra", "contudo", "cuja", "cujas", "cujo", "cujos", 
    "da", "das", "de", "dela", "dele", "deles", "demais", "depois", "desde", "dessa", "desse", "desta", "deste", "disto", 
    "do", "dos", 
    "e", "é", "ela", "elas", "ele", "eles", "em", "enquanto", "entre", "era", "eram", "essa", "essas", "esse", "esses", 
    "esta", "está", "estão", "estas", "estava", "estavam", "este", "estes", "eu", 
    "foi", "foram", 
    "há", 
    "isso", "isto", 
    "já", 
    "lá", "lhe", "lhes", 
    "mais", "mas", "me", "mesma", "mesmas", "mesmo", "mesmos", "meu", "meus", "minha", "minhas", "muito", "muitos", 
    "na", "nas", "nem", "no", "nos", "nós", "nossa", "nossas", "nosso", "nossos", "num", "numa", "nunca", 
    "o", "os", "ou", "onde", 
    "para", "pela", "pelas", "pelo", "pelos", "per", "perante", "pode", "podem", "pois", "por", "porque", "porém", 
    "pouco", "primeiro", 
    "qual", "quais", "quando", "quanto", "que", "quem", 
    "se", "sem", "seu", "seus", "só", "sob", "sobre", "sua", "suas", 
    "tal", "também", "te", "tem", "têm", "tenho", "ter", "teu", "teus", "toda", "todas", "todo", "todos", 
    "tu", "tua", "tuas", "tudo", 
    "um", "uma", "umas", "uns", 
    "vai", "vão", 
    "você", "vocês"
));

    public Search(final InvertedList iv) {
        this.iv = iv;
    }

    public List<String> removeStopWords(final String text) {
        List<String> s = Arrays.asList(text.split(" "));
        s.removeIf(STOP_WORDS::contains);

        return s;
    }

    public void delete(final String text, final int id) {
        List<String> s = removeStopWords(text);
        s.forEach(v -> {
            try {
                this.iv.delete(v, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void create(final String text, final int id) {
        List<String> s = removeStopWords(text);
        HashMap<String, Integer> m = new HashMap<>();

        s.forEach(v -> m.put(v, m.getOrDefault(v, 0) + 1));
        m.forEach((k, v) -> {
            try {
                this.iv.create(k, new ListElement(id, v / s.size()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public List<Integer> search(final String text, final int total) {
        List<String> s = removeStopWords(text);

        HashMap<Integer, Float> m = new HashMap<>();
        s.forEach(v -> {
            List<ListElement> r = List.of();
            try {
                r = Arrays.asList(this.iv.read(v));

                if (r.size() > 0) {
                    float idf = total / r.size();
                    r.forEach(e -> {
                        m.put(e.getId(), m.getOrDefault(e.getId(), 0f) + e.getFrequencia() * idf);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        List<Integer> r = new ArrayList<>(m.keySet());
        r.sort((a, b) -> Float.compare(m.get(b), m.get(a)));
        return r;
    }
}
