#See https://aka.ms/containerfastmode to understand how Visual Studio uses this Dockerfile to build your images for faster debugging.

FROM mcr.microsoft.com/dotnet/core/aspnet:3.1-buster-slim AS base
WORKDIR /app
EXPOSE 80

FROM mcr.microsoft.com/dotnet/core/sdk:3.1-buster AS build
WORKDIR /src
COPY ["src/web/web.csproj", "src/web/"]
COPY ["src/ApplicationCore/ApplicationCore.csproj", "src/ApplicationCore/"]
RUN dotnet restore "src/web/web.csproj"
COPY . .
WORKDIR "/src/src/web"
RUN dotnet build "web.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "web.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "web.dll"]