# PokemonCompose
Projeto baseado na trilha de Android da Ocean com Professores Paulo Salvatore e Sylker Teles

Este é o meu primeiro projeto publico.
Foi feito a partir das aulas dos Professores Paulo Salvatore e Sylker Teles, que foram dadas na trilha de Android na Samsung Ocean,
criei este projeto onde usei o aprendizado das aulas de Retrofit, Room, Jetpack Compose e Arquitetura. 
Para ajudar na interface gráfica usei o projeto do codelabs ( https://developer.android.com/codelabs/jetpack-compose-state?index=..%2F..index#0 ).
Não pude usar o Glide, como foi ensinado na trilha, por incompatibilidade com o Jetpack Compose, optei pelo Coil e foi uma mudança simples.
Uso o Retrofit para buscar os Pokemons na nuvem e o Room para armazena-los no banco de dados locais, junto com o que for adicionado localmente. 
O App lista os Pokemons e permite que se entre o pokemon e o endereço URL da imagem do mesmo.
Ao clicar em um Pokemon você pode editar o nome e a URL. Se mudar um Pokemon da lista da Nuvem, na proxima vez que arregar o aplocativo.
Foram várias adaptações, por exemplo, acesso o Room usando coroutines. Procurei deixar o MainActivity o mais limpo possível.
No ViewModel, seguindo o modelo do codelabs, usei o mutableStateOf ao invés do LiveData.



