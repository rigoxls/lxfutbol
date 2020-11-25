#See https://aka.ms/containerfastmode to understand how Visual Studio uses this Dockerfile to build your images for faster debugging.

FROM mcr.microsoft.com/dotnet/core/sdk:3.1 AS build
WORKDIR /app

COPY . .
COPY src/quotationService/*.csproj ./quotationService/
COPY src/Infraestructure/*.csproj ./Infraestructure/

WORKDIR "quotationService"
RUN dotnet publish -c Release -o out

FROM mcr.microsoft.com/dotnet/core/sdk:3.1 AS runtime
WORKDIR /app
COPY --from=publish /app/publish/out ./ 
ENTRYPOINT ["dotnet", "quotationService.dll"]