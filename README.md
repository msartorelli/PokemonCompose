# PokemonCompose
Projeto baseado na trilha de Android da Ocean com Professores Paulo Salvatore e Antonio de Carvalho

Este é o meu primeiro projeto publico.<br>
Criei este projeto como aprendizado das aulas de Retrofit, Room, Jetpack Compose e Arquitetura. <br>
Para ajudar na interface gráfica usei o projeto do codelabs ( https://developer.android.com/codelabs/jetpack-compose-state?index=..%2F..index#0 ).<br>
Não pude usar o Glide, como foi ensinado na trilha, por incompatibilidade com o Jetpack Compose, optei pelo Coil e foi uma mudança simples.<br>
Uso o Retrofit para buscar os Pokemons na nuvem e o Room para armazena-los no banco de dados local, onde insiro os dados manualmente também.<br> 
O App lista os Pokemons e permite que se digite o nome do pokemon e o endereço URL da imagem do mesmo. Possui um botão para criar um Pokemno aleatório<br>
Ao clicar em um Pokemon você pode editar o nome e a URL. Se mudar um Pokemon da lista da Nuvem, na proxima vez que arregar o aplocativo.<br>
Foram várias adaptações, por exemplo, acesso o Room usando coroutines. Procurei deixar o MainActivity o mais limpo possível.<br>
No ViewModel, seguindo o modelo do codelabs, usei o mutableStateOf ao invés do LiveData.<br>



