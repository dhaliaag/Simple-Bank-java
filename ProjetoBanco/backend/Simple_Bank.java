package ProjetoBanco.backend;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Banco_Simples {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        System.out.println("Digite seu nome:");
        String nome = entrada.next();

        System.out.println("Digite sua senha:");
        String senha = entrada.next();

        double saldo = 0;
        boolean usuarioEncontrado = false;

        // tentar ler o arquivo
        try {
            File arquivo = new File("saldo.txt");
            Scanner leitor = new Scanner(arquivo);

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                String[] partes = linha.split(":");

                // 🔥 AGORA VERIFICA NOME E SENHA
                if (partes[0].equals(nome) && partes[1].equals(senha)) {
                    saldo = Double.parseDouble(partes[2]);
                    usuarioEncontrado = true;
                    break;
                }
            }

            leitor.close();

        } catch (Exception e) {
            System.out.println("Arquivo ainda não existe, vamos criar um novo.");
        }

        if (!usuarioEncontrado) {
            System.out.println("Novo usuário criado!");
            saldo = 0;
        }

        while (true) {

            System.out.println("\n=== Banco da Dhalia ===");
            System.out.println("1 - Ver saldo");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - Sair");
            System.out.print("Escolha: ");

            int opcao = entrada.nextInt();

            if (opcao == 1) {
                System.out.println("💰 Seu saldo é: " + saldo);

            } else if (opcao == 2) {
                System.out.print("Valor para depósito: ");
                double valor = entrada.nextDouble();

                if (valor > 0) {
                    saldo += valor;
                    System.out.println("✅ Depósito realizado!");
                } else {
                    System.out.println("❌ Valor inválido!");
                }

            } else if (opcao == 3) {
                System.out.print("Valor para saque: ");
                double valor = entrada.nextDouble();

                if (valor > saldo) {
                    System.out.println("❌ Saldo insuficiente!");
                } else if (valor <= 0) {
                    System.out.println("❌ Valor inválido!");
                } else {
                    saldo -= valor;
                    System.out.println("💸 Saque realizado!");
                }

            } else if (opcao == 4) {
                System.out.println("Saindo...");
                break;

            } else {
                System.out.println("Opção inválida!");
            }

            // 🔥 SALVAR COM SENHA AGORA
            try {
                File arquivo = new File("saldo.txt");
                Scanner leitor = new Scanner(arquivo);

                StringBuilder novoConteudo = new StringBuilder();
                boolean atualizado = false;

                while (leitor.hasNextLine()) {
                    String linha = leitor.nextLine();
                    String[] partes = linha.split(":");

                    // atualiza só o usuário correto
                    if (partes[0].equals(nome)) {
                        novoConteudo.append(nome + ":" + senha + ":" + saldo + "\n");
                        atualizado = true;
                    } else {
                        novoConteudo.append(linha + "\n");
                    }
                }

                leitor.close();

                // se não existir ainda, cria
                if (!atualizado) {
                    novoConteudo.append(nome + ":" + senha + ":" + saldo + "\n");
                }

                FileWriter escritor = new FileWriter("saldo.txt");
                escritor.write(novoConteudo.toString());
                escritor.close();

            } catch (Exception e) {
                System.out.println("Erro ao salvar dados.");
            }
        }

        entrada.close();
    }
}