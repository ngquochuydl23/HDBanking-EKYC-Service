FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
USER app
WORKDIR /app
EXPOSE 12000
ENV ASPNETCORE_URLS=http://+:12000

FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src


COPY ["Clothes.Commons/Clothes.Commons.csproj", "Clothes.Commons/"]
RUN dotnet restore "Clothes.Commons/Clothes.Commons.csproj"

COPY ["Services/Clothes.Discount.Api/Clothes.Discount.Api.csproj", "Services/Clothes.Discount.Api/"]
RUN dotnet restore "Clothes.Commons/Clothes.Commons.csproj"
RUN dotnet restore "Services/Clothes.Discount.Api/Clothes.Discount.Api.csproj"


COPY . .
WORKDIR /src

RUN dotnet build "Clothes.Commons/Clothes.Commons.csproj" -c Release -o /app/build
RUN dotnet build "Services/Clothes.Discount.Api/Clothes.Discount.Api.csproj" -c Release -o /app/build


FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "Services/Clothes.Discount.Api/Clothes.Discount.Api.csproj" -c Release -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "Clothes.Discount.Api.dll"]