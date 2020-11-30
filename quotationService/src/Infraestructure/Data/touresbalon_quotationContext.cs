using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace Infraestructure.sakila
{
    public partial class touresbalon_quotationContext : DbContext
    {
        public touresbalon_quotationContext()
        {
        }

        public touresbalon_quotationContext(DbContextOptions<touresbalon_quotationContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Quotation> Quotation { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseMySQL("server=tb-providers.cff5umpa7ju3.us-east-1.rds.amazonaws.com;port=3306;database=touresbalon_quotation;uid=root;password=docemonos");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Quotation>(entity =>
            {
                entity.ToTable("quotation");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.EndDate)
                    .HasColumnName("end_date")
                    .HasColumnType("date");

                entity.Property(e => e.IdLodging)
                    .HasColumnName("id_lodging")
                    .HasColumnType("decimal(10,0)");

                entity.Property(e => e.IdSpectacle)
                    .HasColumnName("id_spectacle")
                    .HasColumnType("decimal(10,0)");

                entity.Property(e => e.IdTransport)
                    .HasColumnName("id_transport")
                    .HasColumnType("decimal(10,0)");

                entity.Property(e => e.NumPeople)
                    .HasColumnName("num_people")
                    .HasColumnType("decimal(10,0)");

                entity.Property(e => e.QuotationStatus)
                    .HasColumnName("quotation_status")
                    .HasColumnType("decimal(10,0)");

                entity.Property(e => e.StartDate)
                    .HasColumnName("start_date")
                    .HasColumnType("date");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
