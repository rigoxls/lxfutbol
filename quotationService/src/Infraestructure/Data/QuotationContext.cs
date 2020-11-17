using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace Infraestructure.Data
{
    public partial class QuotationContext : DbContext
    {
        public QuotationContext()
        {
        }

        public QuotationContext(DbContextOptions<QuotationContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Quotation> Quotation { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. See http://go.microsoft.com/fwlink/?LinkId=723263 for guidance on storing connection strings.
                optionsBuilder.UseMySQL("server=localhost;port=3306;user=root;password=lxfutbolPass;database=touresbalon_quotation");
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

                entity.Property(e => e.StartDate)
                    .HasColumnName("start_date")
                    .HasColumnType("date");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
