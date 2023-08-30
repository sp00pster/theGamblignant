package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;
import theGamblignant.powers.LuckPower;

import static theGamblignant.VriskaMod.makeCardPath;

public class SilverWind extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(SilverWind.class.getSimpleName());
    public static final String IMG = makeCardPath("silverwind.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 3;

    public SilverWind() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.doesRoll = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int str = roll(1,4,'p');
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, str), str));
        int dex = roll(1,4,'p');
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, dex), dex));
        //todo i think this is subject to the vim problem. check again if it is before trying to fix this
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}
