﻿using HD.Wallet.Shared.Seedworks;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace HD.Wallet.Transaction.Service.Infrastructure.Transactions
{
    public class TransactionEntity : Entity
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.None)]
        [Required]
        public string Id { get; set; }

        public double Amount { get; set; }

        public string SenderAccountId { get; set; }

        public string ReceiverAccountId { get; set; }

        public DateTime TransactionDate { get; set; }

        public TransactionTypeEnum TransactionType { get; set; }

        public TransactionStatusEnum TransactionStatus { get; set; }
       
        public string Description { get; set; }

        public string TransferContent {  get; set; }

        public double BeforeBalance { get; set; }

        public double AfterBalance { get; set; }
    }
}