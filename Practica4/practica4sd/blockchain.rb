require 'digest'
require 'pp'
require_relative 'block'
require_relative 'transaction'

LEDGER = []

def create_first_block
  i = 0
  instance_variable_set("@b#{i}", Block.first(
    { from: "Dutchgrown", to: "Vincent", what: "Tulip Bloemendaal Sunset", qty: 10 },
    { from: "Keukenhof", to: "Anne", what: "Tulip Semper Augustus", qty: 7 }
  ))
  LEDGER << @b0
  pp @b0
  p "============================"
  menu
end

def add_block
	i = 1
	loop do
	  transactions = get_transactions_data
	  instance_variable_set("@b#{i}", Block.next((instance_variable_get("@b#{i - 1}")), transactions))
	  LEDGER << instance_variable_get("@b#{i}")
	  p "============================"
	  pp instance_variable_get("@b#{i}")
	  p "============================"
	  i += 1
	end
  end

def list_all_blocks
  LEDGER.each do |block|
    pp block
    puts "============================"
  end
end

def exit_program
  exit
end

def invalid_choice
  puts "Invalid choice! Please try again."
end

def menu
	puts "Select an option:"
	puts "1. Add a transaction"
	puts "2. List all blocks"
	puts "3. List all transactions"
	puts "4. Save transactions to file"
	puts "5. Exit"
  
	choice = gets.chomp.to_i
  
	case choice
	when 1
	  add_transaction
	when 2
	  list_all_blocks
	when 3
	  list_all_transactions
	when 4
	  save_transactions_to_file
	when 5
	  exit_program
	else
	  invalid_choice
	end
  
	menu
  end
  

def run
  create_first_block
  menu
end

run
