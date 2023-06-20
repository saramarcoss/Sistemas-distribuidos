def get_transactions_data
	def valid_number?(input)
	  input.match?(/^\d+(\.\d+)?$/)
	end
  
	transactions_block ||= []
	blank_transaction = Hash[from: "", to: "", what: "", qty: ""]
	loop do
	  puts ""
	  puts "Enter your name for the new transaction"
	  from = gets.chomp
	  while from.empty?
		puts "This value cannot be empty"
		print "Enter the name for the transaction: "
		from = gets.chomp
	  end
  
	  puts ""
	  puts "What do you want to send ?"
	  what = gets.chomp
	  while what.empty?
		puts "This value cannot be empty."
		print "Enter what you want to send: "
		what = gets.chomp
	  end
  
	  puts ""
	  puts "How much quantity ?"
	  qty = gets.chomp
  
	  while qty.empty? || !valid_number?(qty)
		if qty.empty?
		  puts "This value cannot be empty."
		else
		  puts "This value has to be a number."
		end
		print "Enter a number "
		qty = gets.chomp
	  end
  
	  puts ""
	  puts "Who do you want to send it to ?"
	  to = gets.chomp
	  while to.empty?
		puts "This value cannot be empty."
		print "Enter the destination: "
		to = gets.chomp
	  end
  
	  transaction = Hash[from: "#{from}", to: "#{to}", what: "#{what}", qty: "#{qty}"]
	  transactions_block << transaction
  
	  puts ""
	  puts "Do you want to make another transaction for this block ? (Y/n)"
	  new_transaction = gets.chomp.downcase
  
	  if new_transaction == "n"
		return transactions_block
	  end
	end
  end
  
  def add_transaction
	  latest_block = LEDGER.last
	  latest_block_hash = latest_block.hash
	  new_transactions = get_transactions_data
	  new_block = Block.next(latest_block, new_transactions)
	  LEDGER << new_block
	  puts "New block created with #{new_block.transactions.size} transactions."
	end
	
  
  def list_all_transactions
	LEDGER.each do |block|
	  block.transactions.each do |transaction|
		puts "From: #{transaction[:from]}, To: #{transaction[:to]}, What: #{transaction[:what]}, Qty: #{transaction[:qty]}"
	  end
	end
  end
  
  def save_transactions_to_file
	  puts "Enter file name to save transactions to (without .txt extension): "
	  filename = gets.chomp
	  File.open("#{filename}.txt", "w") do |file|
		LEDGER.each do |block|
		  block.transactions.each do |transaction|
			file.puts "From: #{transaction[:from]}, To: #{transaction[:to]}, What: #{transaction[:what]}, Qty: #{transaction[:qty]}"
		  end
		end
	  end
	  puts "Transactions saved to #{filename}.txt"
	end